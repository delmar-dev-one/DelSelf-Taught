import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


//HUD (Heads-Up Display)

public class EchoesCrossing extends JPanel implements ActionListener, KeyListener {

    private final int largura = 800;
    private final int altura  = 800;

    // Jogador
    private int playerX = 300;
    private int playerY = 510;
    private final int PLAYER_WIDTH = 15;
    private final int PLAYER_HEIGHT = 25;

    // Estado geral
    private boolean gameOver     = false;
    private boolean faseCompleta = false;
    private boolean modoInfinito = false;
    private int pontuacao = 0;
    private int faseAtual = 1;
    private final int Max_Fases = 10;

    // Vidas e tempo
    private int vidas = 2;
    private int tempoFase = 0;

    // Animacao do astronauta
    private int frameAnimacao = 0;
    private int contadorFrame = 0;

    // Power-ups ativos
    private boolean escudoAtivo = false;
    // private int     escudoTicks = 0;
    // private boolean slowAtivo   = false;
    // private int     slowTicks   = 0;

    // Explosao
    private boolean explosaoAtiva = false;
    private int explosaoX, explosaoY, raioExplosao;

    // Tema visual por fase
    private Color corEstrada = new Color(60, 60, 60);
    private Color corCeu     = new Color(21, 220, 29);
    private Color corFaixa   = Color.WHITE;

    // Zona segura intermediaria
    private final int ZONA_Y  = 115;
    private final int ZONA_H  = 20;

    // Listas de objetos no jogo
    private ArrayList<Carro>     carros     = new ArrayList<>();
    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
    private ArrayList<PowerUp>   powerUps   = new ArrayList<>();
    private ArrayList<Particula> particulas = new ArrayList<>();
    private ArrayList<Estrela>   estrelas   = new ArrayList<>();

    // Recordes
    private ArrayList<Integer> recordes = new ArrayList<>();
    private final String Arq_Recordes = "recordes.txt";

    private Random rand = new Random();
    private Timer  timer;

    // ================================================================
    //  CONSTRUTOR
    // ================================================================
    public EchoesCrossing() {
        setPreferredSize(new Dimension(largura, altura));
        setBackground(new Color(210, 230, 29));
        addKeyListener(this);
        setFocusable(true);
        criarEstrelas();
        carregarRecordes();
        criarCarros();
        timer = new Timer(15, this);
        timer.start();
    }

    // ================================================================
    //  ESTRELAS 
    // ================================================================
    private void criarEstrelas() {
        estrelas.clear();
        for (int i = 0; i < 80; i++) { 
            int vel = rand.nextInt(2) + 1;
            estrelas.add(new Estrela(rand.nextInt(largura), rand.nextInt(120), vel));
        }
    }

    // ================================================================
    //  TEMA VISUAL POR FASE
    // ================================================================
    private void definirTemaFase() {
        int t = faseAtual % 10;
        if      (t == 1) { corEstrada = new Color(60,60,60);   corCeu = new Color(21,22,29);  corFaixa = Color.WHITE; }
        else if (t == 2) { corEstrada = new Color(80,50,30);   corCeu = new Color(30,15,10);  corFaixa = new Color(255,200,100); }
        else if (t == 3) { corEstrada = new Color(30,50,80);   corCeu = new Color(5,10,40);   corFaixa = new Color(100,200,255); }
        else if (t == 4) { corEstrada = new Color(50,70,50);   corCeu = new Color(10,25,10);  corFaixa = new Color(100,255,150); }
        else if (t == 5) { corEstrada = new Color(50,90,50);   corCeu = new Color(10,25,10);  corFaixa = new Color(100,255,150); }
        else if (t == 6) { corEstrada = new Color(50,120,50);   corCeu = new Color(10,25,10);  corFaixa = new Color(100,255,150); }
        else if (t == 7) { corEstrada = new Color(50,75,50);   corCeu = new Color(10,25,10);  corFaixa = new Color(100,255,150); }
        else if (t == 8) { corEstrada = new Color(50,100,60);   corCeu = new Color(10,25,10);  corFaixa = new Color(100,255,150); }
        else if (t == 9) { corEstrada = new Color(50,70,50);   corCeu = new Color(10,25,10);  corFaixa = new Color(100,255,150); }
        else             { corEstrada = new Color(60,20,60);   corCeu = new Color(20,5,25);   corFaixa = new Color(220,100,255); }
    }

    // ================================================================
    //  CRIAR CARROS / OBSTACULOS / POWER-UPS
    // ================================================================
    private void criarCarros() {
        carros.clear();
        obstaculos.clear();
        powerUps.clear();
        definirTemaFase();
        tempoFase = 0;

        int qtdCarros, velSpeed;
        int extra = modoInfinito ? Math.max(0, (faseAtual - Max_Fases) / 2) : 0;


        // Definindo a quantidade de carros e velocidade deles
        if (faseAtual <= 3) { qtdCarros = 2; velSpeed = 3; }
        else if (faseAtual <= 6) { qtdCarros = 4; velSpeed = 5; }
        else if (faseAtual <= 8) { qtdCarros = 5; velSpeed = 5; }
        else { qtdCarros = 6; velSpeed = 7; }

        velSpeed = Math.min(velSpeed + extra, 12);
        int espacof = 92; // espacoFaixas

for (int i = 0; i < qtdCarros; i++) 
        carros.add(new Carro(60 + i * 130, 140, velSpeed + rand.nextInt(2), Color.RED));

        for (int i = 0; i < qtdCarros + 1; i++) 
            carros.add(new Carro(30 + i * 185, 140 + espacof, -(velSpeed + 1 + rand.nextInt(4)), Color.BLACK));

        for (int i = 0; i < qtdCarros; i++) 
            carros.add(new Carro(90 + i * 190, 140 + espacof * 2, velSpeed + 2 + rand.nextInt(3), Color.BLUE));

        for (int i = 0; i < qtdCarros + 1; i++) 
            carros.add(new Carro(20 + i * 175, 140 + espacof * 3, -(velSpeed + 3 + rand.nextInt(3)), Color.MAGENTA));
        
        // Power-ups
        powerUps.add(new PowerUp(50  + rand.nextInt(700), 200 + rand.nextInt(200), PowerUp.ESCUDO));
        if (faseAtual >= 2) powerUps.add(new PowerUp(50 + rand.nextInt(700), 200 + rand.nextInt(200), PowerUp.SLOW));
        if (faseAtual >= 3) powerUps.add(new PowerUp(50 + rand.nextInt(700), 200 + rand.nextInt(200), PowerUp.PONTOS));
    }

    // ================================================================
    //  PARTICULAS DE PO
    // ================================================================
    private void adicionarParticula(int x, int y) {
        for (int i = 0; i < 4; i++) {
            particulas.add(new Particula(
                x + rand.nextInt(10), y + rand.nextInt(6),
                rand.nextInt(5) - 2,  rand.nextInt(3) - 2,
                15 + rand.nextInt(15)));
        }
    }

    // ================================================================
    //  EXPLOSAO
    // ================================================================
    private void ativarExplosao(int x, int y) {
        explosaoAtiva = true;
        explosaoX     = x;
        explosaoY     = y;
        raioExplosao  = 0;
    }

    // ================================================================
    //  DESENHO
    // ================================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- Ceu / Fundo
        g2.setColor(corCeu);
        g2.fillRect(0, 0, largura, 125);

        // --- Estrelas (parallax)
        for (Estrela s : estrelas) {
            int alpha = 120 + s.brilho * 40;
            g2.setColor(new Color(255, 255, 255, alpha));
            g2.fillOval(s.x, s.y, s.tamanho, s.tamanho);
        }

        // --- Estrada
        g2.setColor(corEstrada);
        g2.fillRect(0, 120, largura, 500);

        // --- Faixas centrais
        g2.setColor(corFaixa);
        for (int x = 30; x < largura; x += 115) {
            g2.fillRect(x, 215, 45, 8);
            g2.fillRect(x, 301, 45, 8);
            g2.fillRect(x, 387, 45, 8);
        }

        // --- Zona segura intermediaria
        g2.setColor(new Color(0, 10, 120, 120));
        g2.fillRect(0, ZONA_Y, largura, ZONA_H - 5);
        g2.setColor(new Color(100, 75, 90));
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("ZONA SEGURA", largura / 2 - 44, ZONA_Y + 15);

        // --- Calcada / chao
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 520, largura , altura - 120);

        // --- Base segura (chegada)
        g2.setColor(new Color(0, 140, 0));
        g2.fillRect(0, 65, largura, 55);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Base SEGURA - CHEGADA", 260, 105);

        // --- Power-ups
        for (PowerUp p : powerUps) {
            if (!p.coletado) {
                Color c = p.tipo == PowerUp.ESCUDO ? new Color(0,180,255)
                        : p.tipo == PowerUp.SLOW   ? new Color(180,0,255)
                        :                            new Color(255,215,0);
                g2.setColor(c);
                g2.fillRoundRect(p.x, p.y, 22, 22, 6, 6);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                String lbl = p.tipo == PowerUp.ESCUDO ? "S" : p.tipo == PowerUp.SLOW ? "T" : "$";
                g2.drawString(lbl, p.x + 7, p.y + 15);
            }
        }

        // --- Carros (visuais melhorados)
        for (Carro c : carros) {
            g2.setColor(c.cor);
            g2.fillRoundRect(c.x, c.y, 55, 30, 8, 8);
            g2.setColor(c.cor.darker());
            g2.fillRect(c.x + 5,  c.y + 5, 18, 12);
            g2.fillRect(c.x + 32, c.y + 5, 18, 12);


            // Farol do Carro
            g2.setColor(new Color(255, 255, 180, 200));
            if (c.velocidade > 0) g2.fillOval(c.x + 47, c.y + 10, 9, 9);
            else g2.fillOval(c.x - 2,  c.y + 10, 9, 9);


            // Rodas
            g2.setColor(Color.DARK_GRAY);
            g2.fillOval(c.x + 5,  c.y + 23, 12, 10);
            g2.fillOval(c.x + 38, c.y + 23, 12, 10);
        }

        // --- Particulas de po
        for (Particula p : particulas) {
            int a = (int)(255f * p.vida / p.vidaMax);
            g2.setColor(new Color(200, 150, 80, Math.max(0, a)));
            g2.fillOval(p.x, p.y, 4, 4);
        }

        // --- Explosao
        if (explosaoAtiva) {
            for (int r = raioExplosao; r > 0; r -= 10) {
                int a = Math.max(0, 200 - r * 3);
                g2.setColor(new Color(255, r < 20 ? 50 : 150, 0, a));
                g2.fillOval(explosaoX - r, explosaoY - r, r * 2, r * 2);
            }
        }

        // --- Astronauta
        if (!gameOver || explosaoAtiva) {
            if (!explosaoAtiva) {
                // Escudo visual
                if (escudoAtivo) {
                    g2.setColor(new Color(0, 180, 255, 70));
                    g2.fillOval(playerX - 6, playerY - 6, PLAYER_WIDTH + 12, PLAYER_HEIGHT + 12);
                    g2.setColor(new Color(0, 180, 255, 180));
                    g2.drawOval(playerX - 6, playerY - 6, PLAYER_WIDTH + 12, PLAYER_HEIGHT + 12);
                }
                // Capacete
                g2.setColor(new Color(200, 220, 255));
                g2.fillOval(playerX + 4, playerY, 20, 20);
                // Visor
                g2.setColor(new Color(150, 220, 255, 200));
                g2.fillRect(playerX + 8, playerY + 5, 10, 9);
                // Tronco
                g2.setColor(new Color(200, 220, 255));
                g2.fillRect(playerX + 5, playerY + 18, 18, 14);
                // Pernas animadas
                g2.setColor(new Color(170, 195, 235));
                if (frameAnimacao == 0) {
                    g2.fillRect(playerX + 6,  playerY + 32, 7, 9);
                    g2.fillRect(playerX + 15, playerY + 29, 7, 9);
                } else {
                    g2.fillRect(playerX + 6,  playerY + 29, 7, 9);
                    g2.fillRect(playerX + 15, playerY + 32, 7, 9);
                }
            }
        }

        // --- HUD topo
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));

        // Esquerda
        g2.drawString("ECHOES CROSSING - Delmar", 20, 30);
        g2.drawString("Fase: " + faseAtual + (modoInfinito ? " [INF]" : "/" + Max_Fases), 20, 52);
        g2.drawString("Pontos: " + pontuacao, 20, 74);

        // Direita - Vidas 
        g2.setColor(Color.WHITE);
        g2.drawString("Vidas:", largura - 240, 30);

        for (int i = 0; i < vidas; i++) {
            g2.setColor(Color.RED);
            g2.fillOval(largura - 170 + i * 35, 13, 22, 22);  // bolinhas maiores e mais espaçadas
            g2.setColor(Color.BLACK);
            g2.drawOval(largura - 170 + i * 35, 13, 22, 22);  // contorno para ficar mais bonito
        }

        // Tempo
        int seg = tempoFase / 60;
        g2.setColor(Color.WHITE);
        g2.drawString("Tempo: " + seg + "s", largura - 240, 55);

        // // Power-ups ativos
        // if (escudoAtivo) {
        //     g2.setColor(new Color(0, 180, 255));
        //     g2.drawString("ESCUDO " + (escudoTicks / 60) + "s", largura - 185, 75);
        // }
        // if (slowAtivo) {
        //     g2.setColor(new Color(180, 0, 255));
        //     g2.drawString("SLOW   " + (slowTicks / 60) + "s", largura - 185, 95);
        // }

        // --- Tela: FASE COMPLETA
        if (faseCompleta) {
            g2.setColor(new Color(0, 255, 120, 200));
            g2.fillRect(0, 0, largura, altura);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 46));
            g2.drawString("Fase " + faseAtual + " Concluida com Sucesso!", 100, 250);
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            g2.drawString("Tempo: " + seg + "s     Pontos adquiridos: " + pontuacao, 210, 300);
            g2.drawString("Carregando proxima fase...", 210, 345);
        }

        // --- Tela: GAME OVER
        if (gameOver && !explosaoAtiva) {
            g2.setColor(new Color(180, 0, 0, 210));
            g2.fillRect(0, 0, largura, altura);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 52));
            g2.drawString("Que pena você perdeu!", 215, 200);
            g2.setFont(new Font("Arial", Font.PLAIN, 22));
            g2.drawString("Pontuacao final: " + pontuacao, 275, 248);
            g2.drawString("Tecle [R] para Jogar novamente", 275, 285);
            g2.drawString("Tecle [M] para Modo Infinito",   275, 315);
            if (!recordes.isEmpty()) {
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                g2.setColor(new Color(255, 215, 0));
                g2.drawString("--- TOP RECORDES ---", 265, 370);
                g2.setColor(Color.WHITE);
                for (int i = 0; i < Math.min(5, recordes.size()); i++) {
                    g2.drawString((i + 1) + ". " + recordes.get(i) + " pts", 315, 395 + i * 26);
                }
            }
        }

        // --- Tela: VITORIA
        // !  = operador NOT 
        if (!modoInfinito && faseAtual > Max_Fases && !faseCompleta) { 
            g2.setColor(new Color(255, 215, 0, 210));
            g2.fillRect(0, 0, largura, altura);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 52));
            g2.drawString("VOCE VENCEU, PÁRABENS!", 175, 250);
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            g2.drawString("Pontuacao: " + pontuacao, 295, 305);
            g2.drawString("Tecle [R] para Novo jogo e [M] para Modo Infinito", 150, 355);
        }
    }

    // ================================================================
    //  LOOP DO JOGO
    // ================================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        // So anima a explosao enquanto esta ocorrendo
        if (explosaoAtiva) {
            raioExplosao += 7;
            if (raioExplosao > 120) explosaoAtiva = false;
            repaint();
            if (gameOver) return;
        }

        if (gameOver || faseCompleta) { repaint(); return; }

        // Contadores
        tempoFase++;

        // Parallax: move estrelas
        for (Estrela s : estrelas) {
            s.x -= s.velocidade;
            if (s.x < 0) s.x = largura;
        }

        // Animacao do astronauta
        if (++contadorFrame >= 10) { frameAnimacao = (frameAnimacao + 1) % 2; contadorFrame = 0; }

        // // Tick dos power-ups
        // if (//escudoAtivo && --escudoTicks <= 0) escudoAtivo = false;
        // if (slowAtivo   && --slowTicks   <= 0) slowAtivo   = false;

        // Move carros (reduzindo a velocidade pela metadedos carros)
        int fs = escudoAtivo ? 2 : 1;
        for (Carro c : carros) {
            c.x += c.velocidade / fs;
            if (c.velocidade > 0 && c.x >  largura) c.x = -80;
            if (c.velocidade < 0 && c.x < -80) c.x = largura + 30;
        }

        // Atualiza particulas
        particulas.removeIf(p -> p.vida <= 0);
        for (Particula p : particulas) { p.x += p.vx; p.y += p.vy; p.vida--; }

        // Colisao com carros
        Rectangle pr = new Rectangle(playerX + 4, playerY + 4, PLAYER_WIDTH - 8, PLAYER_HEIGHT - 8);
        if (!escudoAtivo) {
            for (Carro c : carros) {
                if (new Rectangle(c.x, c.y, 55, 30).intersects(pr)) {
                    sofrerDano();
                    break;
                }
            }
        }

        // // Colisao com buracos
        // if (!gameOver && !escudoAtivo) {
        //     for (Obstaculo o : obstaculos) {
        //         if (new Rectangle(o.x, o.y, 42, 22).intersects(pr)) {
        //             sofrerDano();
        //             break;
        //         }
        //     }
        // }

        // Coletar power-ups
        // if (!gameOver) {
        //     Rectangle prFull = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        //     for (PowerUp p : powerUps) {
        //         if (!p.coletado && new Rectangle(p.x, p.y, 22, 22).intersects(prFull)) {
        //             p.coletado = true;
        //             if      (p.tipo == PowerUp.ESCUDO) { escudoAtivo = true; escudoTicks = 300; }
        //             else if (p.tipo == PowerUp.SLOW)   { slowAtivo   = true; slowTicks   = 240; }
        //             else                               { pontuacao += 200; }
        //         }
        //     }
        // }

        // Chegou a base
        if (!gameOver && playerY < 100) {
            int bonus = Math.max(0, 500 - (tempoFase / 60) * 10);
            pontuacao += 300 + faseAtual * 150 + bonus;
            faseCompleta = true;

            Timer delay = new Timer(2200, ev -> {
                faseAtual++;
                boolean vitoria = !modoInfinito && faseAtual > Max_Fases;
                if (vitoria) {
                    salvarRecorde();
                    faseCompleta = false;
                } else {
                    playerX = 300; playerY = 510;
                    faseCompleta  = false;
                    escudoAtivo   = false;
                    criarCarros();
                }
                repaint();
            });
            delay.setRepeats(false);
            delay.start();
        }

        repaint();
    }

    // Logica de dano centralizada
    private void sofrerDano() {
        ativarExplosao(playerX, playerY);
        vidas--; // modo sutil de vidas = vidas - 1;
        if (vidas <= 0) {
            gameOver = true;
            salvarRecorde();
            timer.stop();
        } else {
            playerX = 300;
            playerY = 510;
        }
    }

    // ================================================================
    //  TECLADO
    // ================================================================
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        // Controles nas telas de game over / vitoria
        if (gameOver || (!modoInfinito && faseAtual > Max_Fases)) {
            if (k == KeyEvent.VK_R) reiniciarJogo(false);
            if (k == KeyEvent.VK_M) reiniciarJogo(true);
            return;
        }
        if (faseCompleta) return;

        int passo = 20;
        int ox = playerX, oy = playerY;
        if      (k == KeyEvent.VK_UP)    playerY -= passo;
        else if (k == KeyEvent.VK_DOWN)  playerY += passo;
        else if (k == KeyEvent.VK_LEFT)  playerX -= passo;
        else if (k == KeyEvent.VK_RIGHT) playerX += passo;

        if (playerX != ox || playerY != oy) adicionarParticula(playerX, playerY + 28);

        playerX = Math.max(0,  Math.min(playerX, largura- PLAYER_WIDTH));
        playerY = Math.max(50, Math.min(playerY, altura  - 80));
        repaint();
    }

    // ================================================================
    //  REINICIO
    // ================================================================
    private void reiniciarJogo(boolean infinito) {
        playerX      = 300;
        playerY      = 510;
        gameOver     = false;
        faseCompleta = false;
        modoInfinito = infinito;
        pontuacao    = 0;
        faseAtual    = 1;
        vidas        = 2;
        escudoAtivo  = false;
        explosaoAtiva = false;
        particulas.clear();
        criarCarros();
        timer.start();
    }

    // ================================================================
    //  RECORDES
    // ================================================================
    private void salvarRecorde() {
        recordes.add(pontuacao);
        Collections.sort(recordes, Collections.reverseOrder());// java.util.Collections usado para ordenar os elementos de uma lista (como ArrayList ou LinkedList) em ordem crescente.
        while (recordes.size() > 5) recordes.remove(recordes.size() - 1);
        try (PrintWriter pw = new PrintWriter(new FileWriter(Arq_Recordes))) { // Ele facilita a escrita de tipos primitivos (como int, float) e objetos convertidos para string, oferecendo métodos práticos como print, println e printf
            for (int r : recordes) pw.println(r);
        } catch (IOException ignored) {}
    }

    private void carregarRecordes() {
        recordes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(Arq_Recordes))) { // BufferReader é uma classe usada para ler textos de um fluxo de entrada (como arquivos ou o console) de forma eficiente
            String linha;
            while ((linha = br.readLine()) != null) {
                try { recordes.add(Integer.parseInt(linha.trim())); } // trim() é usado para remover espaços em branco do início e do fim de uma String
                catch (NumberFormatException ignored) {}
            }
        } catch (IOException ignored) {}
        Collections.sort(recordes, Collections.reverseOrder());
    }

    // ================================================================
    //  STUBS DE INTERFACE
    // ================================================================
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e)    {}

    // ================================================================
    //  MAIN
    // ================================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Echoes - Delmar Atravessando ");
            frame.add(new EchoesCrossing());
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // ================================================================
    //  CLASSES INTERNAS
    // ================================================================

    class Carro {
        int x, y, velocidade;
        Color cor;
        Carro(int x, int y, int vel, Color cor) {
            this.x = x; this.y = y; this.velocidade = vel; this.cor = cor;
        }
    }

    class Obstaculo {
        int x, y;
        Obstaculo(int x, int y) { this.x = x; this.y = y; }
    }

    static class PowerUp {
        static final int ESCUDO = 0, SLOW = 1, PONTOS = 2;
        int x, y, tipo;
        boolean coletado = false;
        PowerUp(int x, int y, int tipo) { this.x = x; this.y = y; this.tipo = tipo; }
    }

    class Particula {
        int x, y, vx, vy, vida, vidaMax;
        Particula(int x, int y, int vx, int vy, int vida) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy;
            this.vida = vida; this.vidaMax = vida;
        }
    }

    class Estrela {
        int x, y, tamanho, velocidade, brilho;
        Estrela(int x, int y, int vel) {
            this.x = x; this.y = y; this.tamanho = vel;
            this.velocidade = vel; this.brilho = new Random().nextInt(3);
        }
    }
}