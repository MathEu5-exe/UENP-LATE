import greenfoot.*;

public class Plataforma extends Actor
{
    private int largura = 64; // Tamanho maior para preencher a tela
    private int altura = 64;

    public Plataforma() {
        // Se já definiu uma imagem no Greenfoot, ela será reescalada.
        // Se preferir carregar por nome de arquivo, use: new GreenfootImage("chao.png");
        GreenfootImage img = getImage();
        img.scale(largura, altura);
        setImage(img);
    }
}