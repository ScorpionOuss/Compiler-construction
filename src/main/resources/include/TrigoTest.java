import java.lang.Math ;

public class TrigoTest {

  //double pi = 3.14159265359;

//calculer la factorielle d un entier
  public static long fact(long number){
    if(number==0) return 1;
    if (number==1) {
      return 1;
    }
    return number*fact(number-1);
  }
//calcul du cosinus avec le dévellopement en série entière et en utilisant une formule de récurence
  public static double fonction(double x, int n){
    if(n==0) return 1;
    else{
      return fonction(x ,n-1) + (Math.pow(-1, n)/fact(2*n))*Math.pow(x, 2*n);
    }


  }
//la fonction cosinus de la biblio math java pour tester la précision de notre fonction cos implementé
  public static double realcos(double x){
    return (Math.cos(x));
  }
//valeur absolue
  public static double valeur_absolue(double x){
    if(x>0) return x;
    if(x==0) return 0;
    else{
      return -x ;
    }
  }
//retourne le modulo 2pi d'une valeur donnée
  public static double modulo_2pi(double x){
    double y=3.14159265358979323846264338379;
    while(x>2*y) x-=2*y;
    while(x<0) x+=2*y;
    return x;
  }
// fonction principale de test
  public static void main(String []args){
    double pi = 3.14159265359;
//liste des valeur à tester pour le cosinus
    double [] test_cos = {1.57, 3.26 ,0.785 , 0 ,-3.14159265359 ,560 ,310 ,205 };
    System.out.println("***********************************************************************");
    System.out.println("************************** test cosinus ********************************");

    for (double x : test_cos){
      System.out.println("***********************************************************************");
      System.out.println("valeur inseré   :"+x);
      System.out.println("la valeur réelle est :     "+realcos(x));
      System.out.println("la valeur calculée est :   "+fonction(modulo_2pi(x),16));
      System.out.println(" la différence est :       "+valeur_absolue(realcos(x)-fonction(modulo_2pi(x),16)));

    }
      System.out.println("***********************************************************************");
      System.out.println("************************** test sinus ********************************");

      System.out.println(" not implemented yet ");
      System.out.println("***********************************************************************");
      System.out.println("************************** test arctan ********************************");
      System.out.println(" not implemented yet ");






  }


}
