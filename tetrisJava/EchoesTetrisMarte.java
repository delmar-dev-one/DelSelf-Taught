import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class EchoesTetrisMarte extends JPanel implements ActionListener, KeyListener {

    private final int COLUNAS = 10;
    private final int LINHAS = 20;
    private final int TAM_BLOCO = 30;

    private int[][] board = new int[LINHAS][COLUNAS];
    private int[][] currentPiece;
    private int currentX, currentY;
    private int currentRotation = 0;
    private int score = 0;
    private int oxygen = 100;
    private int level = 1;
    private boolean gameOver = false;
    private Timer timer;
    private long startTime;
    private int oxygenTimer = 60;

    // Estrelas piscando no fundo
    private final int NUM_ESTRELAS = 80;
    private int[] startX = new int[NUM_ESTRELAS];
    private int[] startY = new int[NUM_ESTRELAS];
    private int[] startSpeed = new int[NUM_ESTRELAS];

    // Tetrominos corrigidos
    private final int[][][][] TETROMINOS = {
        { // I
            {{1,1,1,1}},
            {{1},{1},{1},{1}},
            {{1,1,1,1}},
            {{1},{1},{1},{1}}
        },
        { // O
            {{1,1},{1,1}},
            {{1,1},{1,1}},
            {{1,1},{1,1}},
            {{1,1},{1,1}}
        },
        { // T
            {{0,1,0},{1,1,1}},
            {{1,0},{1,1},{1,0}},
            {{1,1,1},{0,1,0}},
            {{0,1},{1,1},{0,1}}
        },
        { // L
            {{1,0,0},{1,1,1}},
            {{1,1},{1,0},{1,0}},
            {{1,1,1},{0,0,1}},
            {{0,1},{0,1},{1,1}}
        },
        { // J
            {{0,0,1},{1,1,1}},
            {{1,0},{1,0},{1,1}},
            {{1,1,1},{1,0,0}},
            {{1,1},{0,1},{0,1}}
        },
        { // S
            {{0,1,1},{1,1,0}},
            {{1,0},{1,1},{0,1}},
            {{0,1,1},{1,1,0}},
            {{1,0},{1,1},{0,1}}
        }
    };

    private final Color[] CORES_MARTE = {
        new Color(255, 100, 50),
        new Color(255, 180, 60),
        new Color(200, 50, 0),
        new Color(220, 120, 40),
        new Color(180, 60, 20),
        new Color(255, 140, 70),
        new Color(150, 30, 10)
    };

    public EchoesTetrisMarte() {
        setPreferredSize(new Dimension(COLUNAS * TAM_BLOCO + 340, LINHAS * TAM_BLOCO));
        setBackground(new Color(3, 3, 18));

        // Inicialização estrelas
        Random rand = new Random();
        for (int i = 0; i < NUM_ESTRELAS; i++) {
            startX[i] = rand.nextInt(COLUNAS * TAM_BLOCO + 300);
            startY[i] = rand.nextInt(LINHAS * TAM_BLOCO);
            startSpeed[i] = rand.nextInt(3) + 1;
        }

        addKeyListener(this);
        setFocusable(true);

        startTime = System.currentTimeMillis();
        timer = new Timer(520, this);
        timer.start();

        novaPeca();
    }

    private void novaPeca() {
        Random rand = new Random();
        int tipo = rand.nextInt(TETROMINOS.length);
        currentRotation = 0;
        currentPiece = TETROMINOS[tipo][currentRotation];
        currentX = COLUNAS / 2 - currentPiece[0].length / 2;
        currentY = 0;

        if (colisao()) {
            gameOver = true;
            timer.stop();
        }
    }

    private int getTipoAtual() {
        for (int i = 0; i < TETROMINOS.length; i++) {
            if (currentPiece == TETROMINOS[i][0] || 
                currentPiece == TETROMINOS[i][1] ||
                currentPiece == TETROMINOS[i][2] ||
                currentPiece == TETROMINOS[i][3]) {
                return i;
            }
        }
        return 0;
    }

    private void rotacionar() {
        int oldRotation = currentRotation;
        currentRotation = (currentRotation + 1) % 4;
        int tipoAtual = getTipoAtual();
        currentPiece = TETROMINOS[tipoAtual][currentRotation];

        if (colisao()) {
            if (!tentarDeslocar(1) && !tentarDeslocar(-1)) {
                currentRotation = oldRotation;
                currentPiece = TETROMINOS[tipoAtual][currentRotation];
            }
        }
    }

    private boolean tentarDeslocar(int dx) {
        currentX += dx;
        if (colisao()) {
            currentX -= dx;
            return false;
        }
        return true;
    }

    private boolean colisao() {
        for (int y = 0; y < currentPiece.length; y++) {
            for (int x = 0; x < currentPiece[y].length; x++) {
                if (currentPiece[y][x] == 0) continue;
                int nx = currentX + x;
                int ny = currentY + y;
                if (nx < 0 || nx >= COLUNAS || ny >= LINHAS) return true;
                if (ny < 0) continue;
                if (board[ny][nx] != 0) return true;
            }
        }
        return false;
    }

    private void fixarPeca() {
        for (int y = 0; y < currentPiece.length; y++) {
            for (int x = 0; x < currentPiece[y].length; x++) {
                if (currentPiece[y][x] == 1) {
                    if (currentY + y >= 0 && currentY + y < LINHAS) {
                        board[currentY + y][currentX + x] = 1;
                    }
                }
            }
        }
        limparLinhas();
        novaPeca();
    }

    private void limparLinhas() {
        int linhasLimpa = 0;
        for (int y = LINHAS - 1; y >= 0; y--) {
            boolean cheia = true;
            for (int x = 0; x < COLUNAS; x++) {
                if (board[y][x] == 0) cheia = false;
            }

            if (cheia) {
                linhasLimpa++;
                for (int k = y; k > 0; k--) {
                    System.arraycopy(board[k - 1], 0, board[k], 0, COLUNAS);
                }
                y++;
            }
        }

        if (linhasLimpa > 0) {
            score += linhasLimpa * 250 * level;
            if ((score % 100) < 50) {
                oxygen = Math.min(100, oxygen + 10);
            }
            atualizarNivel();
        }
    }

    private void atualizarOxigenioTimer() {
        long tempoDecorrido = (System.currentTimeMillis() - startTime) / 1000;
        oxygenTimer = Math.max(0, 60 - (int) tempoDecorrido);
    }

    private void atualizarNivel() {
        int novoLevel = (score / 999) + 1;
        if (novoLevel > level) {
            level = novoLevel;
            int novaVelocidade = Math.max(40, 520 / level);
            timer.setDelay(novaVelocidade);
        }
    }

    private void moverPeca(int dx, int dy) {
        currentX += dx;
        if (colisao()) {
            currentX -= dx;
            if (dy != 0) {
                currentY += dy;
                if (colisao()) {
                    currentY -= dy;
                    fixarPeca();
                }
            }
        } else {
            currentY += dy;
            if (colisao()) {
                currentY -= dy;
                fixarPeca();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Fundo do universo
        g2.setColor(new Color(3, 3, 18));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Nebulosa sutil
        g2.setColor(new Color(60, 20, 50, 50));
        g2.fillOval(-150, 50, 700, 500);

        // Estrelas piscando
        for (int i = 0; i < NUM_ESTRELAS; i++) {
            int brilho = (int) (Math.sin(System.currentTimeMillis() / (150.0 + i * 10)) * 80 + 175);
            g2.setColor(new Color(255, 255, 255, brilho));
            g2.fillRect(startX[i], startY[i], 2, 2);
        }

        // Area de Tabuleiro
        g2.setColor(new Color(35, 22, 15));
        g2.fillRect(0, 0, COLUNAS * TAM_BLOCO, LINHAS * TAM_BLOCO);

        // Blocos fixos
        for (int y = 0; y < LINHAS; y++) {
            for (int x = 0; x < COLUNAS; x++) {
                if (board[y][x] == 1) {
                    g2.setColor(new Color(210, 95, 45));
                    g2.fillRect(x * TAM_BLOCO, y * TAM_BLOCO, TAM_BLOCO, TAM_BLOCO);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x * TAM_BLOCO, y * TAM_BLOCO, TAM_BLOCO, TAM_BLOCO);
                }
            }
        }

        // Peça Atual
        if (currentPiece != null) {
            for (int y = 0; y < currentPiece.length; y++) {
                for (int x = 0; x < currentPiece[y].length; x++) {
                    if (currentPiece[y][x] == 1) {
                        int px = (currentX + x) * TAM_BLOCO;
                        int py = (currentY + y) * TAM_BLOCO;
                        g2.setColor(CORES_MARTE[0]);
                        g2.fillRect(px, py, TAM_BLOCO, TAM_BLOCO);
                        g2.setColor(new Color(255, 220, 180));
                        g2.drawRect(px + 3, py + 3, TAM_BLOCO - 6, TAM_BLOCO - 6);
                    }
                }
            }


            //aSTRONAUTA NO CANTO SEGURANDO A PLACA
            drawAstronaut(g2, COLUNAS * TAM_BLOCO + 45, 260);

            //hud
            g2.setColor(Color.white);
            g2.setFont(new Font("Monospaced", Font.BOLD, 26));
            g2.drawString("ECHOES: SOBREVIVENTE SOLAR", COLUNAS * TAM_BLOCO + 50, 55);


            g2.setFont(new Font("Monospaced", Font.PLAIN, 16));
            g2.drawString("Marte - Sobrevivencia", COLUNAS * TAM_BLOCO + 50, 90);

            g2.drawString("Nível: " + level, COLUNAS * TAM_BLOCO + 50, 125);
            g2.drawString("Pontos: " + score, COLUNAS * TAM_BLOCO + 50, 155);

            g2.setColor(oxygen > 30 ? new Color(255,200,50) : Color.RED);
            g2.fillRect(COLUNAS * TAM_BLOCO + 50, 185, oxygen * 2, 25);
            g2.setColor(Color.WHITE);
            g2.drawRect(COLUNAS * TAM_BLOCO + 50, 185, 200, 25);
            g2.drawString("OXIGÊNIO: " + oxygen + "%", COLUNAS * TAM_BLOCO + 55, 205);
            g2.drawString("TEMPO: " + oxygenTimer + "s", COLUNAS * TAM_BLOCO + 50, 245);


            if (gameOver) {
                g2.setColor(new Color(180, 0, 0, 230));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Monospaced", Font.BOLD, 38));
                g2.drawString("GAME OVER", 110, 240);
                int pontuacaoFinal = score + (oxygen * 10);
                g2.drawString("PONTUAÇÃO FINAL: " + pontuacaoFinal, 95, 290);
            }
        }

    }
    private void drawAstronaut(Graphics2D g, int x, int y) {
        // Corpo
        g.setColor(new Color(230, 230, 240));
        g.fillRect( x + 12, y + 45, 48, 75); // placa
        
        //capacete
        g.setColor(Color.WHITE);
        g.fillOval(x + 18, y + 15, 38, 38); // cabeça

        // visor
        g.setColor(new Color(80, 170, 255));
        g.fillOval(x + 24, y + 22, 26, 24);

        // braços segurando a placa
        g.setColor(new Color(210, 210, 220));
        g.fillRect(x + 55, y + 50, 28, 15); // braço direito

        //Placa
        g.setColor(new Color(30, 30, 45));
        g.fillRect(x + 80, y + 38, 95, 62); // placa
        g.setColor(new Color(255, 215, 100));
        g.setFont(new Font("Monospaced", Font.BOLD, 13));
        g.drawString("PONTOS", x + 80, y + 55);
        g.setFont(new Font("Monospaced", Font.BOLD, 19));
        g.drawString(String.valueOf(score), x + 92, y + 80);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;
        
        currentY++;
        if (colisao()) {
            currentY--;
            fixarPeca();
        }

        if (Math.random() < 0.0045) oxygen = Math.max(0, oxygen - 1);

        atualizarOxigenioTimer();
        atualizarNivel();

        if (oxygen <= 0) {
            gameOver = true;
        }

        repaint();


    }
       
    // Controles do jogo
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        switch (e.getKeyCode()) { // Mover para esquerda
            case KeyEvent.VK_LEFT:
                currentX--; if (colisao()) currentX++;
                break;
            case KeyEvent.VK_RIGHT: // Mover para direita
                currentX++; if (colisao()) currentX--;
                break;
            case KeyEvent.VK_DOWN: // Acelerar queda
                currentY++; if (colisao()) currentY--;
                break;
            case KeyEvent.VK_UP: // Rotacionar
                rotacionar();
                break;
            case KeyEvent.VK_SPACE: // Queda rápida
                while (!colisao()) {
                    currentY++;
                }
                currentY--;
                fixarPeca();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Echoes: Sobrevivente Solar - Tetris - Marte");
        EchoesTetrisMarte game = new EchoesTetrisMarte();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}