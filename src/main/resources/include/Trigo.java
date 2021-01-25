/**
class Math : implémente les fonction de la classe java.Math
*/
 public class Trigo{
	      protected float pi = 3.1415926536f;
        protected float PiS2 = 1.5707963267949f;
	      protected float posInf = Float.POSITIVE_INFINITY;
        protected float negInf = Float.NEGATIVE_INFINITY;
        protected float MAXVALUE = 3.4028234663852886E38f;
        protected float MINVALUE = 1.4E-45f;
        protected float NaN = 0.0f/0.0f;
        protected float FPREC = 0.000001f;

/**
*calcule le min de 2 flottants
*@param flottants
*@return le min des deux flottants passés en argument
*/
float _min(float a, float b) {
    if (a < b) {
        return a;
    } else {
        return b;
    }
}
/**
*calcule le max de 2 flottants
*@param flottants
*@return le min des deux flottants passés en argument
*/
float _max(float a, float b) {
    if (a > b) {
        return a;
    } else {
        return b;
    }
}
/**
*calcule le factoriel d'un nombre entier
*@param int
*@return le factoriel du nombre entier passé en argument
*/
public static float fact(int n){
  if (n==1 || n==0){
    return 1;
  }
  else{
    return n*fact(n-1);
  }
}
/**
*calcule la puissance d'un nombre flottant
*@param flottant,int
*@return  x^n
*/
	float _pow(float x, int n){
		if (x == 0) {
	        return 0;
	        }
      		float ex = x;
		if (n == 0){
	        return 1;
	        }
		else if(n>0){
		while ( n>1) {
		x = x*ex ;
		n--;
		}
		return x ;
		}
		else{
		x = _pow(ex, -n) ;
		return 1/x ;
		}
		}

/**
* Calcule ulp(x) .
*@param flottant dont on calcule son ulp.
*@return flottant correspond au nombre d'ulp du flottant x.
*/
		 float _ulp(float x) {
	 		int ex = 0;
	 		if(x<=0){
				x = -x;
			}
	 		if (x == posInf || x == negInf){
				 return x;}
	 		else if (x == NaN) {
				 return NaN;
					 }
	 	  	else if (x == 0.0 || x == -0.0) {
				 return MINVALUE;
					 }
	 		else if (x == MAXVALUE) {
				 return _pow(2, 104);
					 }
			else if ( x >= 1 ) {
			 		ex-- ;
			 		while (x >= 1 ) {
			 			x =  x/(float)2.0;
			 			ex ++ ;
			 		}
					return _pow(2,ex - 23);
			 	}
		 	else{
		 		while ( x < 1) {
		 			x = x*(float)2.0;
		 			ex -- ;
		 		}
				return _pow(2,ex - 23);
		 	}
    }

/**
* Calcule x%y .
*@param flottant .
*@return flottant correspond au nombre x%y.
*/
float _fmod(float x, float y) {
    return x - y * (int)(x/y);
}

/**
*  Calcule la valeur absolue d"un flottant .
*@param flottant dont on calcule sa valeur absolue.
*@return flottant correspond à la valeur aboslue de x.
*/

float _abs(float a) {
        if (a < 0) {
                a = -a;
        }
        return a;
}
/**
*  Calcule le signe d"un flottant .
*@param flottant .
*@return 1 si l'argument est positif, -1 sinon.
*/

int _signe(float z){
  int s;
  if (z > 0){
    s = 1;
  } else {
    s = -1;
  }
  return s;
}

/**
* Calcule le cosinus de x et renvoie le résultat.
*@param flottant dont on calcule son cosinus.
*@return flottant correspondant au cosinus de x.
*/
		float _cosTaylor(float f){
			 float res = 1f;
			 float r = 0.1f;
			 int i = 2;
			 float x = -_pow(f,2)/i;
			 int k = 0;
			 while(k<1){
				 k++;
				 res +=x;
				 x*=-1*_pow(f,2)/((i+1)*(i+2));
				 i+=2;
			 }
			 return res;
}
/**
* Calcule le cosinus de x et renvoie le résultat.
*@param flottant dont on calcule son cosinus.
*@return flottant correspondant au cosinus de x.
*/
public float _cos(float x){
  if((0<=x && x<=1.652f)) {
    return _cosTaylor(x);
  }
  else if(1.652<=x && x<4.5){
    return  _sinTaylor(pi/2-x);
  }
  else {
    return  -_cosTaylor(pi-x);
  }
}

/**
* Calcule le sinus de x et renvoie le résultat.
*@param flottant dont on calcule son sin.
*@return flottant correspondant au sinus de x.
*/
float _sinTaylor(float x) {
    float mul = 1;
    int i = 1;
    float sum;
    float factor;
    if (x == 0) {
        return 0;
    }
    factor = x;
    sum = x;
    int k =0;
    while (k<=100) {
      k++;
        factor = -factor * x * x / (2*i * (2*i+1));
        sum = sum + factor;
        i = i + 1;
    }
    return sum * mul;
}

/**
* Calcule le sinus de x et renvoie le résultat.
*@param flottant dont on calcule son sin.
*@return flottant correspondant au sinus de x.
*/
public float _sin(float x){
  x = _fmod(x,2*pi);
  if((0<=x && x<0.7853981633974483f)) {
    return _sinTaylor(x);
  }
  else if(0.7853981633974483f<=x && x<PiS2){
    return  _cos(PiS2-x);
  }
  else if(PiS2<=x && x<2.356194490192345f){
    return  -_cos(PiS2+x);
  }
  else if(2.356194490192345f<=x && x<pi){
    return  _sinTaylor(pi-x);
  }
  else if(pi<=x && x<3.9269908169872414f){
    return  _cos(x-pi/2);
  }
  else if(3.9269908169872414f<=x && x<5.497787143782138f){
    return  -_sinTaylor(x-pi);
  }
  else {
    return  -_cos(x+PiS2);
  }
}



/**
* Calcule le arcsinus de x et renvoie le résultat.
*@param flottant dont on calcule son arcsin.
*@return flottant correspondant au arcsinus de x.
*/
float asinTaylor(float x) {
    float res = x;
    float prov = x;
    int k = 1;
    while (k < 1000000) {
        prov = prov * x * x * k / ((k+1)*(2*k+1));
        res +=prov;
        k++;
    }
    return res;
}

/**
* Calcule le arcsinus de x et renvoie le résultat.
*@param flottant dont on calcule son arcsin.
*@return flottant correspondant au arcsinus de x.
*/
public  float asinse(float x){
  int n=35;
  int N=n-1;
  boolean bool= true;
  float res=fact(n-1)/(_pow(2,n-1)*fact((n-1)/2)*fact((n-1)/2)*(n));
  while(N>=0){
    if (bool==true){
      res=res*x;
      bool=false;
    }else{
      res=res*x+fact(N-1)/(_pow(2,N-1)*fact((N-1)/2)*fact((N-1)/2)*(N));
      bool=true;
    }
    N=N-1;
  }
  return res;
}

/**
* Calcule le arcsinus de x et renvoie le résultat.
*@param flottant dont on calcule son arcsin.
*@return flottant correspondant au arcsinus de x.
*/
public  float _asin(float x){
  if(_abs(x)>1){
    System.out.println("ArgumentError");
    return 0;
  }
  if(_abs(x)<=0.72){
    return asinTaylor(x);
  }
  else if(x>0.72){
    return pi/2-asinTaylor((float)_sqrt(1-_pow(x,2)));
  }
  else{
    return asinTaylor((float)_sqrt(1-_pow(x,2)))-pi/2;
  }
}
/**
* Calcule le racine carrée de x et renvoie le résultat.
*@param flottant dont on calcule son racine carrée.
*@return flottant correspondant au racine carrée de x.
*/
float _sqrt(float a){//précision à 1 ulp
  int i = 0;
  int n =15;
  float x = a;
  if (a < 0){
    System.out.println("ERREUR : argument de sqrt() négatif");
    return NaN;
  }
  if (a == 0){
    return 0;
  }
  while (i < n){
    x = 1 / 2 * (x + a / x);
    i = i + 1;
  }
  return x;
}

/**
* Calcule l'arctan de x et renvoie le résultat.
*@param flottant dont on calcule son arctan.
*@return flottant correspondant à l'arctan de x.
*/
public float atanHermite(float x){
	float tab[]= {1.1097301E-6f,-3.1647858E-5f, 4.3415572E-4f, -0.0038123499f,
	0.024052639f, -0.11600586f, 0.44423878f, -1.3847452f, 3.5731058f, -7.719477f,
	14.067447f, -21.717817f, 28.45957f, -31.659622f, 29.87223f,-23.889599f,16.206028f,
	-9.34463f ,4.565837f,-1.8463271f,0.59336287f,-0.17059685f,0.06747529f,-0.0061950684f,
	-0.0315158f,-5.6966146E-5f,0.034484863f,-0.037037037f,0.04f,-0.04347826f,0.04761905f,
	-0.05263158f,0.05882353f,-0.06666667f,0.07692308f,-0.09090909f,0.11111111f,-0.14285715f,
	0.2f,-0.33333334f};
	int index[]={55,54,53,52,51,50,49,48,47,46,45,44,
    43,42,41,40,39,38,37,36,35,34,33,32,31,
   	 30,29,27,25,23,21,19,17,15,13,11,9,7,5,3};
	float resu =0;
	for(int i=39;i>=0;i--){
		resu += _pow(x,index[i])*tab[i];
	}
	resu +=x;
			return resu;
}
public  float atanHermite1(float x){
  float res=
      (_pow(x,55)/901120f) -(7f*_pow(x,54)/221184f) +(377f*_pow(x,53)/868352f) -(203f*_pow(x,52)/53248f) +(10049f*_pow(x,51)/417792f) -(11879f*_pow(x,50)/102400f) +(178321f*_pow(x,49)/401408f)-(68063f*_pow(x,48)/49152f)+(2751463f*_pow(x,47)/770048f)-(1454473f*_pow(x,46)/188416f )+(10371647f*_pow(x,45)/737280f)-(489259f*_pow(x,44)/22528f)
      +(5012527f*_pow(x,43)/176128f)-(1361617f*_pow(x,42)/43008f)+(5016623f*_pow(x,41)/167936f)-(489259f*_pow(x,40)/20480f)+(10355263f*_pow(x,39)/638976f)-(1454473f*_pow(x,38)/155648f)  +(2767847f*_pow(x,37)/606208f)-(68063f*_pow(x,36)/36864f)  +(170129f*_pow(x,35)/286720f)  -(11879f*_pow(x,34)/69632f)+(18241f*_pow(x,33)/270336f)-(203f*_pow(x,32)/32768f)
      -(16007f*_pow(x,31)/507904f)  -(7f*_pow(x,30)/122880f)  +(565f*_pow(x,29)/16384f)  -(_pow(x,27)/27f)  +(_pow(x,25)/25f)  -(_pow(x,23)/23f)  +(_pow(x,21)/21f)  -(_pow(x,19)/19f)  +(_pow(x,17)/17f)
      -(_pow(x,15)/15f)+(_pow(x,13)/13f)-(_pow(x,11)/11f)+(_pow(x,9)/9f)-(_pow(x,7)/7f)+(_pow(x,5)/5f)-(_pow(x,3)/3f)+x;
    return res;

}

/**
* Calcule l'arctan de x et renvoie le résultat.
*@param flottant dont on calcule son arctan.
*@return flottant correspondant à l'arctan de x.
*/


public  float _atan(float x){
  int minus = 1 ;
  //Par imparité on se ramène à [O;+infini]
  if (x<0){
    minus = -1 ;
  }
  //On utilise la formule de trigonométrie : atan(x) + atan(1/x) = +-PI/2.
  if(x>1.1875){
    return minus*(PiS2-atanHermite(1/x));
  }
  else if (x>0.6875f){
    return minus*(atanHermite((x -1f)/(1f+x)) + PiS2/4.0f);
  }
  else{
    return minus*atanHermite(x);
  }
}

	public static void main(String[] args){
		Trigo t = new Trigo();
		float NaN = 0.0f/0.0f;
		float MAXVALUE = 3.4028234663852886E38f;
		float posInf = Float.POSITIVE_INFINITY;
    float negInf = Float.NEGATIVE_INFINITY;
		float pi = 3.1415926536f;
		float PiS2 = 1.5707963267949f;

    int Error = 0 ;
		int nb = 0 ;
		float diff = 0 ;
		double MoyErr = 0 ;
		float MaxErr = 0 ;
/////////////////////////////////test precision ulp/////////////////////////////
for (float x = 0f ; x< pi/4; x = x + (float) Math.pow(2,0)){
	nb ++ ;
	diff = Math.abs((float)Math.ulp(x) - t._ulp(x));
	if (diff>= t._ulp(x)) {
		MoyErr += diff/t._ulp((float)Math.ulp(x));
		Error ++;
	}

}
MoyErr /=Error ;
System.out.println("fin du test de ulp. Erreur rencontré : "+ Error+" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "
 + MoyErr+", Erreur max en ulp : "
 + MaxErr + "\n----------------------------------------------------------------------\n");
 /////////////////////////////Test precision cos/////////////////////////////////////////
 // Error=0;
 // nb=0;
 // MoyErr = 0 ;
 // MaxErr = 0 ;
 // diff = 0 ;
 // for(float x = 0; x <=0.1 ; x = x + (float) Math.pow(2,-11)){
 //   diff = Math.abs( t._cosx) - (float)Math.cos(x) );
 //   MaxErr = (diff/t._ulp((float)Math.cos(x)) >MaxErr)?diff/t._ulp((float)Math.cos(x)):MaxErr;
 //   nb ++ ;
 //   MoyErr +=	diff/t._ulp((float)Math.cos(x));
 //   if ( diff > t._ulp((float)Math.cos(x)) ){
 //     Error ++;
 //   }
 // }
 // MoyErr /=Error ;
 // System.out.println("fin du test de cos. Erreur rencontré : "+ Error+" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "
 //  + MoyErr+", Erreur max en ulp : "
 //  + MaxErr);
 // Error=0;
 // nb=0;
 // MoyErr = 0 ;
 // MaxErr = 0 ;
 // diff = 0 ;
 // for(float x = 0; x <=10 ; x = x + (float) Math.pow(2,-11)){
 //   diff = Math.abs( t._atan(x) - (float)Math.atan(x) );
 //   MaxErr = (diff/t._ulp((float)Math.atan(x)) >MaxErr)?diff/t._ulp((float)Math.atan(x)):MaxErr;
 //   nb ++ ;
 //   MoyErr +=	diff/t._ulp((float)Math.atan(x));
 //   if ( diff > t._ulp((float)Math.atan(x)) ){
 //     Error ++;
 //   }
 // }
 // MoyErr /=Error ;
 // System.out.println("fin du test de arctan. Erreur rencontré : "+ Error+" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "
 //  + MoyErr+", Erreur max en ulp : "
 //  + MaxErr);
 //
 //
 //
 //
 //  Error=0;
 //  nb=0;
 //  MaxErr = 0 ;
 //  MoyErr = 0 ;
 //  for(float x = 0; x < 1000 ; x = x + (float) Math.pow(2,-5)){
 //    diff =Math.abs(((float)Math.atan(x) - t._atan(x)));
 //    MaxErr = (diff/t._ulp((float)Math.atan(x)) >MaxErr)?diff/t._ulp((float)Math.atan(x)):MaxErr;
 //    nb ++ ;
 //    if ( diff >t._ulp((float)Math.atan(x))){
 //      MoyErr += diff/Math.ulp((float)Math.atan(x));
 //      Error ++;
 //    }
 //
 //  }
 //  MoyErr /=Error;
 //  System.out.println("fin du test de arctan. Erreur rencontré : "+" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "
 //    + MoyErr+", Erreur max en ulp : "
 //    + MaxErr);
    // Error=0;
    // nb=0;
    // MaxErr = 0 ;
    // MoyErr = 0 ;
    // for(float x =-1; x < 1 ; x = x + (float) Math.pow(2,-5)){
    //   diff =Math.abs(((float)Math.asin(x) - t._asin(x)));
    //   MaxErr = (diff/t._ulp((float)Math.asin(x)) >MaxErr)?diff/t._ulp((float)Math.asin(x)):MaxErr;
    //   nb ++ ;
    //   if ( diff >t._ulp((float)Math.asin(x))){
    //     MoyErr += diff/Math.ulp((float)Math.asin(x));
    //     Error ++;
    //   }
    //
    // }
    // MoyErr /=Error;
    // System.out.println("fin du test de arcsin. Erreur rencontré : "+" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "
    //   + MoyErr+", Erreur max en ulp : "
    //   + MaxErr);

      // Error=0;
      // nb=0;
      // MaxErr = 0 ;
      // MoyErr = 0 ;
      // for(float x =0; x < pi/3 ; x = x + (float) Math.pow(2,-5)){
      //   diff =Math.abs(((float)Math.sin(x) - t._sin(x)));
      //   MaxErr = (diff/t._ulp((float)Math.sin(x)) >MaxErr)?diff/t._ulp((float)Math.sin(x)):MaxErr;
      //   nb ++ ;
      //   if ( diff >t._ulp((float)Math.sin(x))){
      //     MoyErr += diff/Math.ulp((float)Math.sin(x));
      //     Error ++;
      //   }
      //
      // }
      // MoyErr /=Error;
      // System.out.println("fin du test de sin. Erreur rencontré : "+" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "
      //   + MoyErr+", Erreur max en ulp : "
      //   + MaxErr);



}}
