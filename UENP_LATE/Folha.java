import greenfoot.*;

public class Folha extends Item
{
    public Folha() {
        // Carrega, redimensiona para 30x30px e APLICA ao ator
        GreenfootImage imgFolha = carregarEScalar("Folha.png", 50, 50);
        setImage(imgFolha);
    }

    @Override
    public void aplicarEfeito(Aluno aluno) {
        aluno.coletarFolha();
    }
}