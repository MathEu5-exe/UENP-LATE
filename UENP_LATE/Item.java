/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import greenfoot.*;

public abstract class Item extends Actor
{
    public abstract void aplicarEfeito(Aluno aluno);
    // instance variables - replace the example below with your own'
    public Item()
    {
    }
    
    protected GreenfootImage carregarEScalar(String nomeArquivo, int largura, int altura) {
        GreenfootImage img = new GreenfootImage(nomeArquivo);
        img.scale(largura, altura);
        return img;
    }

}
