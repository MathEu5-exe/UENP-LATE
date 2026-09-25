import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot e MouseInfo)

public class MyWorld extends World
{
    private Aluno aluno;

    public MyWorld()
    {    
        super(800, 600, 1); 

        // Define a ordem de camadas na tela (Ator na frente, plataforma atrás)
        setPaintOrder(Aluno.class, Item.class, Plataforma.class);

        montarFundo();
        prepararCenario();
    }

    @Override
    public void act() {
        atualizarHUD();
        checarFimDeJogo();
    }

    private void montarFundo() {
        GreenfootImage fundo = new GreenfootImage("country-platform-back.png");
        GreenfootImage floresta = new GreenfootImage("country-platform-forest.png");

        fundo.scale(getWidth(), getHeight());
        floresta.scale(getWidth(), getHeight());

        fundo.drawImage(floresta, 0, 0);
        setBackground(fundo);
    }

    private void prepararCenario() {
    // 1. Instancia o Aluno
    aluno = new Aluno();
    addObject(aluno, 100, 400);

    // 2. Chão contínuo com blocos de 64px
    // O loop pula de 64 em 64 pixels (x += 64)
    for (int x = 32; x < getWidth() + 32; x += 64) {
        Plataforma chao = new Plataforma();
        addObject(chao, x, 568); // Y ajustado para o novo tamanho
    }

    // 3. Plataformas suspensas maiores e bem posicionadas
    addObject(new Plataforma(), 200, 420);
    addObject(new Plataforma(), 264, 420);

    addObject(new Plataforma(), 450, 300);
    addObject(new Plataforma(), 514, 300);

    addObject(new Plataforma(), 680, 200);

    // 4. Espalhando os Itens ajustados (Folhas e Cervejas em cima das plataformas)
    // Folhas
    addObject(new Folha(), 232, 350);
    addObject(new Folha(), 482, 230);
    addObject(new Folha(), 680, 130);
    addObject(new Folha(), 120, 490);
    addObject(new Folha(), 320, 490);
    addObject(new Folha(), 550, 490);
    addObject(new Folha(), 620, 490);
    addObject(new Folha(), 720, 490);
    addObject(new Folha(), 380, 490);
    addObject(new Folha(), 200, 230);

    // Cervejas
    addObject(new Cerveja(), 450, 490);
    addObject(new Cerveja(), 514, 230);
}
    /**
     * Escreve o Placar/HUD direto na tela.
     */
    private void atualizarHUD() {
        if (aluno != null) {
            // Desenha os textos no canto superior esquerdo (X, Y)
            showText("Folhas: " + aluno.getFolhasColetadas() + " / 10", 100, 30);
            showText("Nota Atual: " + aluno.getNotaAtual(), 100, 50);
        }
    }

    /**
     * Regras de Fim de Jogo (Vitória ou Derrota).
     */
    private void checarFimDeJogo() {
        if (aluno == null) return;

        // CONDIÇÃO DE VITÓRIA: Pegou as 10 folhas
        if (aluno.getFolhasColetadas() >= 10) {
            showText("VOCÊ CHEGOU A TEMPO NA PROVA! VITÓRIA!", getWidth() / 2, getHeight() / 2);
            Greenfoot.stop(); // Congela o jogo
        }

        // CONDIÇÃO DE DERROTA 1: Bebeu demais e zera a nota
        if (aluno.getNotaAtual() <= 0) {
            showText("REPROVADO POR NOTA! FIM DE JOGO!", getWidth() / 2, getHeight() / 2);
            Greenfoot.stop();
        }

        // CONDIÇÃO DE DERROTA 2: Caiu num buraco (fora da tela)
        if (aluno.getY() >= getHeight() - 15) {
            showText("VOCÊ CAIU DO MAPA! FIM DE JOGO!", getWidth() / 2, getHeight() / 2);
            Greenfoot.stop();
        }
    }
}