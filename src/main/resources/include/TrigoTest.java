import java.lang.Math ;

/**
 *
 * @author amine angar gl16
 *
 */
public class TrigoTest {


/** la fonction factorielle
 *
 * @param number
 *
 * @return la factorielle de number
 */
  public static long fact(long number){
    if(number==0) return 1;
    if (number==1) {
      return 1;
    }
    return number*fact(number-1);
  }

  /** la valeure absolue
   * @param x
   * @return valeure absolue de x
   */
    public static double valeur_absolue(double x){
      if(x>0) return x;
      if(x==0) return 0;
      else{
        return -x ;
      }
    }

  /** modulo 2pi
   * @param x
   * @return le modulo 2pi de x
   */
    public static double modulo_2pi(double x){
      double y=3.14159265358979323846264338379;
      while(x>2*y) x-=2*y;
      while(x<0) x+=2*y;
      return x;
    }


/** clacule le cosinus en utilisant le développement en série entière
 *
 * @param x , n
 *
 * @return le cosinus de x calculé jusqu'à rang n ( plus on augmente n plus on augmente la précision car on va pousser le dévéloppement en série entière)
 */
  public static double mycos(double x, int n){
    if(n==0) return 1;
    else{
      return mycos(x ,n-1) + (Math.pow(-1, n)/fact(2*n))*Math.pow(x, 2*n);
    }
  }

  /**clacule le sinus en utilisant le développement en série entière
   *
   * @param x , n
   *
   * @return le sinus de x calculé jusqu'à rang n ( plus on augmente n plus on augmente la précision car on va pousser le dévéloppement en série entière)
   */
  public static double mysin(double x, int n){
    if(n==0) return x;
    else{
      return mysin(x ,n-1) + (Math.pow(-1, n)/fact(2*n+1))*Math.pow(x, 2*n+1);
    }
  }

  /**clacule le arctan en utilisant le développement en série entière
   * @param x , n
   *
   * @return l'arctan de x calculé jusqu'à rang n ( plus on augmente n plus on augmente la précision car on va pousser le dévéloppement en série entière)
   */

  public static double myarctan(double x, int n){
    if(n==0) return x;
    else{
      return myarctan(x ,n-1) + (Math.pow(-1, n)/(2*n+1))*Math.pow(x, 2*n+1);
    }
  }

  /** le sinus reél
   *@param x
   *@return la valeur réelle de sinus x
   */
  public static double realsin(double x){
    return (Math.sin(x));
  }

  /** l'arctan reélle
   *@param x
   *@return la valeur reélle d'arctan x
   */
  public static double realarctan(double x){
    return (Math.atan(x));
  }



/** le cosinus reél
 * @param x
 * @return la valeur reélle de cosinus x
 */
  public static double realcos(double x){
    return (Math.cos(x));
  }


// fonction principale de test

  public static void main(String []args){

	  double pi = 3.14159265358979323846264338379;
//liste des valeur à tester pour le cosinus
    double [] test = {1.57, 3.26 ,0.785 , 0 ,pi ,-pi ,pi/2 ,pi/4 ,2*pi ,-2*pi ,-1 ,1 ,45 ,7.56 ,560 ,310 ,255 };


    System.out.println("*************************** DEBUT TEST ************************************");
    System.out.println("***********************************************************************");
    System.out.println("************************** test cosinus ********************************");

    for (double x : test){
      System.out.println("***********************************************************************");
      //Ce commentaire est à effacer après : ce test est calculé jusqu'à un rang 16 , tu peux mettre le rang que tu veux . mais après certain rang ça ne fonctionne plus
      //c'est ce que j'essaie de résoudre
      System.out.println("valeur inseré   :"+x);
      System.out.println("la valeur réelle est :     "+realcos(x));
      System.out.println("la valeur calculée est :   "+mycos(modulo_2pi(x),16));
      System.out.println(" la différence est :       "+valeur_absolue(realcos(x)-mycos(modulo_2pi(x),16)));

    }
      System.out.println("***********************************************************************");
      System.out.println("************************** test sinus ********************************");
      for (double x : test){
        System.out.println("***********************************************************************");
      //Ce commentaire est à effacer après : ce test est calculé jusqu'à un rang 17 , tu peux mettre le rang que tu veux . mais après certain rang ça ne fonctionne plus
        //c'est ce que j'essaie de résoudre
        System.out.println("valeur inseré   :"+x);
        System.out.println("la valeur réelle est :     "+realsin(x));
        System.out.println("la valeur calculée est :   "+mysin(modulo_2pi(x),17));
        System.out.println(" la différence est :       "+valeur_absolue(realsin(x)-mysin(modulo_2pi(x),17)));

      }
      System.out.println("***********************************************************************");
      System.out.println("*************************** fin test sin ***********************************");

      System.out.println("***********************************************************************");
      System.out.println("************************** test arctan ********************************");
      for (double x : test){
        System.out.println("***********************************************************************");
      //Ce commentaire est à effacer après : ce test est calculé jusqu'à un rang 10 , tu peux mettre le rang que tu veux . mais après certain rang ça ne fonctionne plus 
        //c'est ce que j'essaie de résoudre
        System.out.println("valeur inseré   :"+x);
        System.out.println("la valeur réelle est :     "+realarctan(x));
        System.out.println("la valeur calculée est :   "+myarctan(modulo_2pi(x),10));
        System.out.println(" la différence est :       "+valeur_absolue(realarctan(x)-myarctan(modulo_2pi(x),10)));

      }
      System.out.println("***********************************************************************");
      System.out.println("*************************** fin test arctan ***********************************");


      System.out.println(" not implemented yet ");
      System.out.println("***********************************************************************");
      System.out.println("************************** test  ********************************");
      System.out.println(" not implemented yet ");






  }


}
