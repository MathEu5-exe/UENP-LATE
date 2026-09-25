import greenfoot.*;

public class Aluno extends Actor
{
    // --- Configuração de Tamanho do Personagem ---
    private int larguraDesejada = 64; // Reduza para diminuir a largura
    private int alturaDesejada  = 64; // Reduza para diminuir a altura

    // --- Atributos de Física ---
    private int velocidadeHorizontal = 4;
    private int velocidadeVertical = 0;
    private int forcaGravidade = 1;
    private int forcaPulo = -15;

    
    // -- Inventário -- //
    private int folhasColetadas = 0;
    private double notaAtual = 10.0;
    private int timerVelocidadeExtra = 0;
    private int velocidadePadrao = 4;
    
    // --- Imagens do Personagem ---
    private GreenfootImage imgParado;
    private GreenfootImage imgPulando;
    private GreenfootImage imgCaindo;
    private GreenfootImage[] animAndar;
    private GreenfootImage[] animCorrer;

    // Controladores de Animação
    private int frameAnimacao = 0;
    private int timerAnimacao = 0;
    private boolean viradoParaEsquerda = false;

    public Aluno() {
        // Carrega e escala todas as imagens para o novo tamanho
        imgParado  = carregarEScalar("Parado.png");
        imgPulando = carregarEScalar("Pulando1.png");
        imgCaindo  = carregarEScalar("Caindo.png");

        animAndar = new GreenfootImage[] {
            carregarEScalar("Andando1.png"),
            carregarEScalar("Andando2.png")
        };

        animCorrer = new GreenfootImage[] {
            carregarEScalar("Correndo1.png"),
            carregarEScalar("Correndo2.png")
        };

        setImage(imgParado);
    }

    /**
     * Método auxiliar para carregar a imagem e aplicar a escala desejada.
     */
    private GreenfootImage carregarEScalar(String nomeArquivo) {
        GreenfootImage img = new GreenfootImage(nomeArquivo);
        img.scale(larguraDesejada, alturaDesejada);
        return img;
    }

    @Override
    public void act() {
        processarMovimento();
        aplicarGravidade();
        checarPulo();
        checarColetaveis();
        atualizarEfeitos();
        atualizarVisual();
    }

    private void checarColetaveis() {
        Item item = (Item) getOneIntersectingObject(Item.class);
        
        if (item != null) {
            item.aplicarEfeito(this);
            getWorld().removeObject(item);
        }
    }
    
    private void atualizarEfeitos() {
        if (timerVelocidadeExtra > 0) {
            timerVelocidadeExtra--;
            if (timerVelocidadeExtra == 0) {
                setVelocidadeHorizontal(velocidadePadrao);
            }
        }
    }
    
    public void coletarFolha() {
        folhasColetadas++;
    }
    
    public void perderNota(double pontos) {
        notaAtual -= pontos;
        if (notaAtual < 0 ) notaAtual = 0;
    }
    
    public void ativarBoostVelocidade(int novaVelocidade, int duracao) {
        setVelocidadeHorizontal(novaVelocidade);
        timerVelocidadeExtra = duracao;
    }
    
    public int getFolhasColetadas() {
        return folhasColetadas;
    }
    
    public double getNotaAtual() {
        return notaAtual;
    }
    
    private void processarMovimento() {
        boolean movendo = false;

        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - velocidadeHorizontal, getY());
            viradoParaEsquerda = true;
            movendo = true;
        }
        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + velocidadeHorizontal, getY());
            viradoParaEsquerda = false;
            movendo = true;
        }

        // Controle do tempo da animação
        if (movendo && velocidadeVertical == 0) {
            timerAnimacao++;
            
            // Define o intervalo de frames (maior = animação mais lenta/suave)
            int tempoTroca = (velocidadeHorizontal > 5) ? 6 : 12; 
            
            if (timerAnimacao >= tempoTroca) {
                timerAnimacao = 0;
                frameAnimacao = (frameAnimacao + 1) % 2;
            }
        } else {
            // Reseta a animação ao parar de andar
            timerAnimacao = 0;
            frameAnimacao = 0;
        }
    }

    private void aplicarGravidade() {
        velocidadeVertical += forcaGravidade;
        setLocation(getX(), getY() + velocidadeVertical);

        Actor plataforma = getOneIntersectingObject(Plataforma.class);
        if (plataforma != null) {
            velocidadeVertical = 0;
            int alturaPlataforma = plataforma.getImage().getHeight();
            int alturaAluno = getImage().getHeight();
            setLocation(getX(), plataforma.getY() - (alturaPlataforma / 2 + alturaAluno / 2));
        }
    }

    private void checarPulo() {
        if (Greenfoot.isKeyDown("space") && velocidadeVertical == 0) {
            velocidadeVertical = forcaPulo;
        }
    }

    private void atualizarVisual() {
        GreenfootImage imgAtual;

        // 1. Estados no ar
        if (velocidadeVertical < 0) {
            imgAtual = new GreenfootImage(imgPulando);
        } else if (velocidadeVertical > 0) {
            imgAtual = new GreenfootImage(imgCaindo);
        } 
        // 2. Estados no chão
        else if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right")) {
            if (velocidadeHorizontal > 5) {
                imgAtual = new GreenfootImage(animCorrer[frameAnimacao]);
            } else {
                imgAtual = new GreenfootImage(animAndar[frameAnimacao]);
            }
        } else {
            imgAtual = new GreenfootImage(imgParado);
        }

        // Aplica o espelhamento mantendo o estado correto
        if (viradoParaEsquerda) {
            imgAtual.mirrorHorizontally();
        }

        setImage(imgAtual);
    }

    public void setVelocidadeHorizontal(int novaVelocidade) {
        this.velocidadeHorizontal = novaVelocidade;
    }
}