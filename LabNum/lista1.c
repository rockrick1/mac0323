#include <math.h>
#include <float.h>
#include <stdio.h>

// use "gcc lista1.c -lm -olista1"
// para gerar o arquivo lista1 correspondendo ao
// codigo abaixo, e ./lista1 para execuar o
// programa lista1

// Imprimindo um numero com 30 casas decimais,
// no formato cientÃ­fico (que Ã© aproximado),
// seguido do mesmo numero no formato hexadecimal
// (que Ã© exato).

void print(char const* label, long double x)
{
  printf("%s%+38.30Le = %+22.16La, \n", label, x, x);
}

// Estude o arquivo float.h. Verifique se
// o que estÃ¡ nesse arquivo Ã© consistente com
// os resultados das questÃµes 1-4 abaixo.
// (O arquivo float.h Ã© um header padrÃ¢o do C
//  e deve estar em algum lugar na sua mÃ¡quina)

// explique porque a funÃ§Ã£o abaixo calcula
// os respectivos epsilons

void questao_1()
{
  // epsilon para float
  float float_epsilon = 1.0f;
  while( 1.0f + float_epsilon != 1.0f )
  {
    float_epsilon *= 0.5f; // vai diminuindo o epsilon
  }
  float_epsilon *= 2.0f;

  print("float epsilon   = ", float_epsilon);
  print("FLT_EPS         = ", FLT_EPSILON);

  // epsilon para double
  double double_epsilon = 1.0f;
  while( 1.0f + double_epsilon != 1.0f )
  {
    double_epsilon *= 0.5f;
  }
  double_epsilon *= 2.0f;

  print("double epsilon  = ", double_epsilon);
  print("DBL_EPS         = ",  DBL_EPSILON);

  // epsilon para long double
  long double ldouble_epsilon = 1.0f;
  while( 1.0f + ldouble_epsilon != 1.0f )
  {
    ldouble_epsilon *= 0.5f;
  }
  ldouble_epsilon *= 2.0f;

  print("ldouble epsilon = ", ldouble_epsilon);
  print("LDBL_EPS        = ",  LDBL_EPSILON);
}
/*  tanto para float como para double, se formos reduzindo o tamanho de uma
    variável, uma hora ela chegará no seu menor valor "normal", e, a partir dai,
    será possivelmente interpretado como zero.
    Esses valores são baseados no tipo da variável em questão:
        float:  1 bit de sinal, 23 bits de mantissa, 8 bits de expoente
        double: 1 bit de sinal, 52 bits de mantissa, 11 bits de expoente
        ldouble: (aparentemente 96 bits)
    Como dividindo por 2 mudamos apenas o expoente, as representações dos
    epsilons em hexadecimal só varia no expoente.
    Já nas decimais, o double apresenta um epsilon final de ordem
    (ordem do float)²
*/

void questao_2()
{
  // Explique porque o codigo abaixo calcula
  // o maior float finito

  float float_max = 1.0f;
  while( 4.0f * float_max != 8.0f * float_max )
  {
    float_max *= 2.0f;
  }
  // quando 4*floatmax = inf, 2*floatmax será
  // o maior valor possivel de float

  float_max *= (4.0f * (1.0f - 0.5f * FLT_EPSILON));
  print("flt max  = ", float_max);
  print("FLT_MAX  = ", FLT_MAX);

  // Explique porque o codigo abaixo calcula
  // o maior double finito

  double double_max = 1.0;
  while( 4.0 * double_max != 8.0 * double_max )
  {
    double_max *= 2.0;
  }

  double_max *= (4.0 * (1.0 - 0.5 * DBL_EPSILON));
  print("dbl max  = ", double_max);
  print("DBL_MAX  = ", DBL_MAX);

  // Explique porque o codigo abaixo calcula
  // o maior long double finito

  long double ldouble_max = 1.0l;
  while( 4.0f * ldouble_max != 8.0l * ldouble_max )
  {
    ldouble_max *= 2.0l;
  }

  ldouble_max *= (4.0l * (1.0l - 0.5l * LDBL_EPSILON));
  print("ldbl max = ", ldouble_max);
  print("LDBL_MAX = ", LDBL_MAX);
}

void questao_3()
{
  // Explique porque o codigo abaixo calcula
  // o menor float diferente de zero

  float float_min = 1.0f;
  while( 0.5f * float_min != 0.0f )
  {
    float_min*= 0.5f;
  }

  print("flt min      = ", float_min);
  print("FLT_TRUE_MIN = ", FLT_TRUE_MIN);

  // Explique porque o codigo abaixo calcula
  // o menor double diferente de zero

  double double_min = 1.0;
  while( 0.5 * double_min != 0.0 )
  {
    double_min *= 0.5;
  }

  print("dbl min      = ", double_min);
  print("DBL_TRUE_MIN = ", DBL_TRUE_MIN);

  // Explique porque o codigo abaixo calcula
  // o menor long double diferente de zero

  long double ldouble_min = 1.0l;
  while( ldouble_min * 0.5l != 0.0l )
  {
    ldouble_min *= 0.5l;
  }

  print("ldbl min      = ", ldouble_min);
  print("LDBL_TRUE_MIN = ", LDBL_TRUE_MIN);
}

void questao_4()
{
  // Explique porque o codigo abaixo calcula
  // o menor float normal (e explique o que isso
  // significa)

  print("flt min = ", FLT_TRUE_MIN / FLT_EPSILON);
  print("FLT_MIN = ", FLT_MIN);

  // Explique porque o codigo abaixo calcula
  // o menor double normal

  print("dbl min = ", DBL_TRUE_MIN / DBL_EPSILON);
  print("DBL_MIN = ", DBL_MIN);

  // Explique porque o codigo abaixo calcula
  // o menor long double normal

  print("ldbl min = ", LDBL_TRUE_MIN / LDBL_EPSILON);
  print("LDBL_MIN = ", LDBL_MIN);
}

// Resolvendo a equaÃ§Ã£o 10^(-4) x^2 + 16 x + 10^(-4) = 0
// Mostre que em aritmeÃ©tica exata os nÃºmeros
// x1 e y1 sÃ£o iguais, e sao uma raiz da equacao,
// a outra Ã© x2. Descreva quais sÃ£o as diferenÃ§as
// nos resultados e tente explicÃ¡-las

void questao_5()
{
  float bf = 16.0f;
  float af = 1.0e-3f;
  float cf = 1.0e-3f;
  float sf = sqrtf(bf * bf - 4 * af * cf);
  float x1f =   (sf - bf)/ (2 * af);
  float y1f = - (2  * cf)/ (sf + bf);
  float x2f = - (sf + bf)/ (2 * af);

  double bd = bf;
  double ad = af;
  double cd = cf;
  double sd = sqrt(bd * bd - 4 * ad * cd);
  double x1d =  (sd - bd)/ (2 * ad);
  double y1d = -(2  * cd)/ (bd + sd);
  double x2d = -(bd + sd)/ (2 * ad);

  long double bl = bf;
  long double al = af;
  long double cl = cf;
  long double sl = sqrtl(bl * bl- 4 * al * cl);
  long double x1l =  (sl - bl)/ (2 * al);
  long double y1l = -(2  * cl)/ (bl + sl);
  long double x2l = -(bl + sl)/ (2 * al);

  print("x1 float   = ",x1f);
  print("x1 double  = ",x1d);
  print("x2 ldouble = ",x1l);
  printf("----------------------------\n");
  print("y1 float   = ",y1f);
  print("y1 double  = ",y1d);
  print("y1 ldouble = ",y1l);
  printf("----------------------------\n");
  print("x2 float   = ",x2f);
  print("x2 double  = ",x2d);
  print("x2 ldouble = ",x2l);
}

int main()
{
  printf("digite a opcao desejada (de 1 a 5), \n");
  printf("ou '0' para terminar.\n");

  while( 1 )
  {
    int opcao;
    int n = scanf("%d", &opcao);
    if( n != 1 )
    {
      printf("Opcao invalida\n");
    }
    else
    {
      switch( opcao )
      {
        case 0:
        {
          return 0;
        }
        case 1:
        {
          printf("Questao 1\n");
          questao_1();
          break;
        }
        case 2:
        {
          printf("Questao 2\n");
          questao_2();
          break;
        }
        case 3:
        {
          printf("Questao 3\n");
          questao_3();
          break;
        }
        case 4:
        {
          printf("Questao 4\n");
          questao_4();
          break;
        }
        case 5:
        {
          printf("Questao 5\n");
          questao_5();
          break;
        }
        default:
        {
          printf("opcao invalida\n");
          break;
        }
      };
    }

    int c;
    while( ((c = getchar()) != '\n') && (c != EOF));
  }
}

/*
a haiku

ligo meu pc
vem jo go dos ma gui nhos
leaf sheild ex plo diu */

