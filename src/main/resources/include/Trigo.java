public class Trigo{
		protected float pi = 3.1415926536f;
        protected float PiS2 = 1.5707963267949f;
		protected float posInf = Float.POSITIVE_INFINITY;
        protected float negInf = Float.NEGATIVE_INFINITY;
        protected float MAXVALUE = 3.4028234663852886E38f;
        protected float MINVALUE = 1.4E-45f;
        protected float NaN = 0.0f/0.0f;
//////////////////////min max///////////////////////////////////////


float _min(float a, float b) {
    if (a < b) {
        return a;
    } else {
        return b;
    }
}



float _max(float a, float b) {
    if (a > b) {
        return a;
    } else {
        return b;
    }
}



/////////////////////////////factoriel//////////////////////////////




public static float fact(int n){
  if (n==1 || n==0){
    return 1;
  }
  else{
    return n*fact(n-1);
  }

}


//////////////////////Puissance x^n/////////////////////////////////
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
///////////////////ulp(x) les résultats sont compatibles///////////////////////////////////////////
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
///////////////////////modulo 2pi/////////////////////////////////////////

float _Modulo2Pi(float x, float y) {
    return x - y * (int)(x/y);
}


//////////////////////////la valeur absolue//////////////////////////////////


float _abs(float a) {
        if (a < 0) {
                a = -a;
        }
        return a;
}


//////////////////////////signe/////////////////////////////////////////



int _signe(float z){
  int s;
  if (z > 0){
    s = 1;
  } else {
    s = -1;
  }
  return s;
}




///////////////////////Version de cos(serie de taylor)///////////////////






float _cosTaylor(float f){
			 float res = 1;
			 float r = 0.1f;
       float s = 1;
			 int i = 2;
			 float x = -_pow(f,2)/i;
			 float y = 1;
			 int k = 0;
			 while(k<100){
				 k++;
				 res +=x;
				 y=x;
				 x*=-1*_pow(f,2)/((i+1)*(i+2));
				 s = _abs((y-x));
				 i+=2;

			 }
			 return res;
}




public float _cos(float x){
  x = _Modulo2Pi(x, 2*pi);
  if((0<=x && x<pi) ||(x<=0 && x>=-pi/4)) {
    return _cosTaylor(x);
  }
  else if(pi<=x && x<3*pi/2){
    return 0;
  }
  else{
    return 0;
  }
}



////////////////////////////////arcsin Taylor//////////////////////////




float asin1(float x) {
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


public  float asin10(float x){
  if(_abs(x)>1){
    System.out.println("ArgumentError");
    return 0;
  }
  if(_abs(x)<=0.72){
    return asinse(x);
  }
  else if(x>0.72){
    return pi/2-asinse((float)Math.sqrt(1-_pow(x,2)));
  }
  else{
    return asinse((float)Math.sqrt(1-_pow(x,2)))-pi/2;
  }
}
/////////////////////////////Version Hermite///////////////////////////////////////



public float atanHermite(float x){
	float tab[]= {1.1097301E-6f,-3.1647858E-5f, 4.3415572E-4f, -0.0038123499f,
	0.024052639f, -0.11600586f, 0.44423878f, -1.3847452f, 3.5731058f, -7.719477f,
	14.067447f, -21.717817f, 28.45957f, -31.659622f, 29.87223f,-23.889599f,16.206028f,
	-9.34463f ,4.565837f,-1.8463271f,0.59336287f,-0.17059685f,0.06747529f,-0.0061950684f,
	-0.0315158f,-5.6966146E-5f,0.034484863f,-0.037037037f,0.04f,-0.04347826f,0.04761905f,
	-0.05263158f,0.05882353f,-0.06666667f,0.07692308f,-0.09090909f,0.11111111f,-0.14285715f,
	0.2f,-0.33333334f};
	int index[]={55,54,53,52,51,50,49,48,47,46,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,
    30,29,27,25,23,21,19,17,15,13,11,9,7,5,3};
	float resu =0;
	for(int i=39;i>=0;i--){
		resu += _pow(x,index[i])*tab[i];
	}
	resu +=x;
			return resu;
}



float _atan(float x){
		int signe = 1 ;
		if (x<0){
				signe = -1 ;
		}
		if(x>1.1875){
				return signe*(pi/2-atanHermite(1/x));
		}
		else if (x>0.6875f){
				return (((float)signe)*((float) (atanHermite((x -1)/(1+x)) + pi/4.0)));
		}
		else{
				return signe*atanHermite(x);
		}
}

////////////////////////////main pour les tests//////////////////////////////////////

	public static void main(String[] args){
		Trigo t = new Trigo();
		float NaN = 0.0f/0.0f;
		float MAXVALUE = 3.4028234663852886E38f;
		float posInf = Float.POSITIVE_INFINITY;
        float negInf = Float.NEGATIVE_INFINITY;
		float pi = 3.1415926536f;
		float PiS2 = 1.5707963267949f;
		// for(int k =100;k < 10000;k=k+100){
			// int j=0;
			// int k =0;
		// for(float i=00;i<=200;i=i+0.1f){
			// k=k+1;
			// if(t._ulp(i) == Math.ulp(i) ){
				// j++;
				// System.out.println(i);
				// float i=123.1f;
				// System.out.println(i + t._ulp(i) +"         "+ Math.ulp(i)+"           " + t.ulp(i));
			// }
 		// }
		// System.out.printf("%s %s\n",k,j);
// }
		// System.out.println(t._ulp((123.1f)));
		// System.out.println(t._modulo2pi(pi/(2f))==1.5707964f);
		// System.out.println(t._modulo2pi(pi/(1f))==3.1415927f);
		// System.out.println(t._modulo2pi(3*pi/(2f))==4.712389f);
		// System.out.println(t._modulo2pi((-2f)*pi/(1f))==0f);
		// System.out.println(t._cosTaylor(-pi/12));
		// for(int i =0;i<=64;i++){
		// System.out.println(i*pi/16+" "+Math.cos(i*Math.PI/16));}
		// for(int i =0;i<=64;i++){
		// System.out.println(i*pi/16+" "+t._cosTaylor(i*pi/16));}
// 	// }
// System.out.println(t._arctan(0.9222f));
// System.out.println(Math.atan(0.9222f));
// System.out.println(t.atan1(0.9222f));
// System.out.println(t.atanHermite(0.9222f));
// System.out.println(t._atan(0.9222f));
// 	float g =t._pow(2,100);
// 	float x =t._pow(2,80);
// 	while (x != x - g) {
// 		// System.out.println("hh");
// 			g = g / 2;
// 		}
// System.out.println(g);
// }
////////////////////////////////////////////test ulp/////////////////////////////////////////:
int nb = 0 ;
float gap = 0 ;
int Erreur = 0 ;
double MoyErreur = 0 ;
float MaxErreur = 0 ;
//  for (float x = 0 ; x< 10000; x = x + (float) Math.pow(2,-10)){
// 	 nb ++ ;
// 	 gap = Math.abs((float)Math.ulp(x) - t._ulp(x));
//    MaxErreur = (gap/t._ulp((float)Math.ulp(x)) >MaxErreur)?gap/t._ulp((float)Math.ulp(x)):MaxErreur;
// 	 if (gap>= t._ulp(x)) {
// 		 MoyErreur += gap/t._ulp((float)Math.ulp(x));
// 		 Erreur ++;
// 	 }
//  }
//  MoyErreur /=Erreur ;
//  System.out.println(" ulp. Erreur : "+Erreur +" sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "+ MoyErreur+", Erreur max en ulp : "+ MaxErreur+"\n\n---------------------------------------------------------\n");
/////////////////////////////////////////test arctan///////////////////////////////////////
// Erreur=0;
// nb=0;
// MaxErreur = 0 ;
// MoyErreur = 0 ;
// for(float x = 3*pi/4; x < 5*pi/4 ; x = x + (float) Math.pow(2,-11)){
// 	gap =Math.abs(((float)Math.atan(x) - t._atan(x)));
// 	MaxErreur = (gap/t._ulp((float)Math.atan(x)) >MaxErreur)?gap/t._ulp((float)Math.atan(x)):MaxErreur;
// 	nb ++ ;
// 	if ( gap >t._ulp((float)Math.atan(x))){
// 		MoyErreur += gap/Math.ulp((float)Math.atan(x));
// 		Erreur ++;
// 	}
// }
// MoyErreur /=Erreur;
// System.out.println(" arctan. Erreur  : "+Erreur+ " sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "+ MoyErreur+", Erreur max en ulp : "+ MaxErreur+"\n\n---------------------------------------------------------\n");
// // // gap =Math.abs(((float)Math.atan(0.03125f) - t._atan(0.03125f)));
// // // MoyErreur = gap/Math.ulp((float)Math.atan(0.03125f));
// // // System.out.println("hh "+t._atan(0.03125f));
// // // System.out.println("hh "+Math.atan(0.03125f));
// // // System.out.println("hh "+MoyErreur);
// //// System.out.println("Début du test de cos :\n");
// Erreur=0;
// nb=0;
// MaxErreur = 0 ;
// MoyErreur = 0 ;
// for(float x = -pi/4; x < pi/4 ; x = x + (float) Math.pow(2,- 17)){
// 	gap =Math.abs(((float)Math.cos(x) - t._cos(x)));
// 	MaxErreur = (gap/t._ulp((float)Math.cos(x)) >MaxErreur)?gap/t._ulp((float)Math.cos(x)):MaxErreur;
// 	nb ++ ;
// 	if ( gap >t._ulp((float)Math.cos(x))){
// 		MoyErreur += gap/Math.ulp((float)Math.cos(x));
// 		Erreur ++;
// 	}
// }
// MoyErreur /=Erreur;
// System.out.println("cos. Erreur : "+Erreur+ " sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "+ MoyErreur+", Erreur max en ulp : "+ MaxErreur+"\n\n---------------------------------------------------------\n");
//////////////////////////////////////////////cosTaylor///////////////////////////////////
//     Erreur=0;
//     nb=0;
//     MaxErreur = 0 ;
//     MoyErreur = 0 ;
//     for(float x = -pi/4; x < pi/4 ; x = x + (float) Math.pow(2,- 17)){
//     	gap =Math.abs(((float)Math.cos(x) - t._cosTaylor(x)));
//     	MaxErreur = (gap/t._ulp((float)Math.cos(x)) >MaxErreur)?gap/t._ulp((float)Math.cos(x)):MaxErreur;
//     	nb ++ ;
//     	if ( gap >t._ulp((float)Math.cos(x))){
//     		MoyErreur += gap/Math.ulp((float)Math.cos(x));
//     		Erreur ++;
//     	}
//     }
//     MoyErreur /=Erreur;
//     System.out.println("cosTaylor. Erreur : "+Erreur+ " sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "+ MoyErreur+", Erreur max en ulp : "+ MaxErreur+"\n\n---------------------------------------------------------\n");
// //       // taylor 0 et 3pi/4 ////cos 3pi/4 et 5pi/4 /// 5 à 7 cos//7 à 9 cos cordic
//
// // System.out.println(t.asinse(0.71f));
//         Erreur=0;
//         nb=0;
//         MaxErreur = 0 ;
//         MoyErreur = 0 ;
//         for(float x = -1; x < 1 ; x = x + (float) Math.pow(2,- 17)){
//           gap =Math.abs(((float)Math.asin(x) - t.asin10(x)));
//           MaxErreur = (gap/t._ulp((float)Math.asin(x)) >MaxErreur)?gap/t._ulp((float)Math.asin(x)):MaxErreur;
//           nb ++ ;
//           if ( gap >t._ulp((float)Math.asin(x))){
//             MoyErreur += gap/Math.ulp((float)Math.asin(x));
//             Erreur ++;
//           }
//         }
//         MoyErreur /=Erreur;
//         System.out.println("arcsin. Erreur  : "+Erreur+ " sur " +nb+ "tests.\n"+"Erreur moyenne en ulp : "+ MoyErreur+", Erreur max en ulp : "+ MaxErreur+"\n\n---------------------------------------------------------\n");
 }}

