import greenfoot.*;

public class Cerveja extends Item
{
    private double penalidadeNota = 1.0;
    private int velocidadeExtra = 8;
    private int duracaoEfeito = 180;

    public Cerveja() {
        // Carrega, redimensiona para 30x30px e APLICA ao ator
        GreenfootImage imgCerveja = carregarEScalar("Cerveja.png", 50, 50);
        setImage(imgCerveja);
    }

    @Override
    public void aplicarEfeito(Aluno aluno) {
        aluno.perderNota(penalidadeNota);
        aluno.ativarBoostVelocidade(velocidadeExtra, duracaoEfeito);
    }
}