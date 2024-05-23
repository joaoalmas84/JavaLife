package pt.isec.pa.javalife.model;


import pt.isec.pa.javalife.model.data.Area;

//PARA VERIFICAR SE DUAS AREAS ESTÃO SOBREPOSTAS, WIP
public class SobreposicaoCheck{
  /*  public boolean check(Area o1, Area o2, double altura, double largura) {
        Ponto[] po1=new Ponto[4];
        Ponto[] po2=new Ponto[4];
        altura--; //assumindo que a zona seja tipo um array e comece em 0
        largura--;


        //verificar se existem mais pontos que as areas ocupam

        //encontrar um igual quer dizer que estão sobrepostos
        for(Ponto p1:po1){
            for(Ponto p2:po2){
                if(p1.compare(p2))
                    return true;
            }
        }
        return false;

    }

    private Ponto[] getPontos(Area o,double altura,double largura){
        Ponto[] p=new Ponto[4];
        double cima = o.cima();
        double esquerda=o.esquerda();
        double baixo=o.baixo();
        double direita=o.direita();

        p[0]=new Ponto(esquerda,altura-cima);
        p[1]=new Ponto(largura-direita,altura-cima);
        p[2]=new Ponto(esquerda,baixo);
        p[3]=new Ponto(largura-direita,baixo);

        return p;
    }

    private class Ponto{
        public double x,y;
        public Ponto(double x_,double y_){
            x=x_;
            y=y_;
        }

        public boolean compare(Ponto p){
            return this.x==p.x && this.y==p.y;
        }

    }
*/
}


