#include<stdio.h>
#include<string.h>
#include<math.h>

struct details
{
   char name[30];
   int accno;
   long long int amount;
   int num;
};

void customer(int);
void  trans(int);
void amount(int);
void create(int);
void history();

int main(void)
{
        int i,j,x;
        for(i=0;i<66;i++)
        printf("%c",' ');

        for(j=0;j<2;j++)
        {
                 for(i=0;i<22;i++)
                 {
                         printf("%c",'-');
                 }
                 printf("\n");
                 for(i=0;i<66;i++)
                 {
                         printf("%c",' ');
                 }
        }
        for(i=0;i<3;i++)
        {
                printf("%c",'-');
        }
        printf("WELCOME TO T-PAY");
        for(i=0;i<3;i++)
        {
                 printf("%c",'-');
        }
        printf("\n");
        for(i=0;i<66;i++)
        {
             printf("%c",' ');
        }
        for(j=0;j<2;j++)
        {
                for(i=0;i<22;i++)
                {
                        printf("%c",'-');
                }
                printf("\n");
                for(i=0;i<66;i++)
                {
                        printf("%c",' ');
                }
        }
        printf("\n\n");
        int n,a,mp,pn;
        hello :
        printf("Enter 1 to know details\n");
        printf("Enter 2 to money transfer\n");
        printf("Enter 3 to check balance\n");
        printf("Enter 4 to create account\n");
        printf("Enter 5 to open transaction history\n");
        printf("Enter 6 to close\n");

        read : scanf("%d",&n);
        if(n>0)
        {
                if(n==1)
                {

                        printf("Enter 1 to login\n");
                        printf("Enter 2 to exit\n");
                        printf("Enter 3 to close\n");
                         hi :  scanf("%d",&a);
                         if(a>0)
                         {
                                 if(a==1)
                                 {
                                                customer(n);
                                 }
                                 else if(a==2)
                                        goto hello;
                                 else if(a=3)
                                        return 0;
                                 else
                                        goto hi;
                         }
                }
                               else  if(n==2)
                {
                        trans(n);
                        printf("\nEnter 1 to see your transaction history or any other number to exit:\n");
                        scanf("%d",&x);
                        if(x==1)
                        {
                                history(n);
                        }
                        else
                        {
                                return 0;
                        }

                }
                else if(n==3)
                {
                        amount(n);
                }
                else if(n==4)
                {
                        create(n);
                }
                else if(n==5)
                {
                         history();
                }
                else if(n==6)
                {
                        return 0;
                }

                else
                {
                        printf("Enter another number\n");
                        goto read;
                }
        }
        else
        {
                printf("Invalid input\nPlease enter valid number:");
                goto read;
        }
        int re;
        printf("press 0 if you wish to continue with other transactions:");
        scanf("%d",&re);
        if(re==0)
            goto hello;
                              
        printf("\n");
        for(i=0;i<66;i++)
                printf("%c",' ');
        for(j=0;j<2;j++)
        {
                for(i=0;i<28;i++)
                {
                        printf("%c",'-');
                }
                printf("\n");
                for(i=0;i<66;i++)
                        printf("%c",' ');
        }
        for(i=0;i<3;i++)
                printf("%c",'-');

        printf("THANKS FOR USING T PAY");
        for(i=0;i<3;i++)
                printf("%c",'-');
        printf("\n");
        for(i=0;i<66;i++)
                printf("%c",' ');
        for(j=0;j<2;j++)
        {
                for(i=0;i<28;i++)
                {
                         printf("%c",'-');
                }
                printf("\n");
                for(i=0;i<66;i++)
                         printf("%c",' ');
        }
        printf("\n\n");

        return 0;
}

void customer(int n)
{
    int h,i,k,pin,j;
    printf("Enter your account number\n");
    printf("Enter 20 to login as manager:\n");
    scanf("%d",&h);
    struct details d[30];

      strcpy(d[1].name,"dilip");
      strcpy(d[2].name,"imran");
                                          strcpy(d[3].name,"manoj");
      strcpy(d[4].name,"abhi");
      strcpy(d[5].name,"harsha");
      strcpy(d[6].name,"bhavani");
      strcpy(d[7].name,"bhanu");
      strcpy(d[8].name,"kiran");
      strcpy(d[9].name,"chaitu");
      strcpy(d[10].name,"praneetha");
      strcpy(d[11].name,"fahad");
      strcpy(d[12].name,"viroop");
      for(i=1;i<20;i++)
      {
              d[i].accno=i;
         d[i].num=123;
      }
      if(h<20)
      {
         j=4;
         pass:     printf("please enter your pin:\n");
          scanf("%d",&pin);
          if(pin==1)
          {
                printf("Account holder name is %s\n",d[h].name);
                printf("Account number is acc%d\n",d[h].accno);
                printf("Phone number is %d\n",d[h].num);
          }
          else
          {
            --j;
            printf("wrong pin %d chances remaining:\n",j);
            if(j==0)
                goto exit;
             goto pass;
          }
      }
      if(h==20)
      {
                  i=4;
                 read:    printf("Enter the password :\n");
                 scanf("%d",&k);
                 if(k==1)
                {
                        for(i=1;i<13;i++)
                        {
                                printf("%s=acc%d\n",d[i].name,d[i].accno);

                        }
                }
                else
                {
                              --i;
                              printf("Incorrect password\n %d chances remaining\n",i);
                              if(i==0)
                                   goto exit;
                              goto read;
                }
        }
        exit:           i=0;
}


void amount(int n)
{
      char acc[30];
      int k,h,i;
 read:     printf("Enter the password :\n");
      scanf("%d",&k);
        if(k==1)
        {
                 printf("Enter the account holder name :\n");
                scanf("%s",acc);
                for(i=0;i<20;i++)
                {
                        if(i==h)
                        {
                                printf("The account balance is :");
                                 FILE *fp;
                                fp=fopen(acc,"r");
                                 while(!feof(fp))
                                        putchar(fgetc(fp));
                                fclose(fp);
                        }

                }
        }
       else
        {
                --i;
                 printf("Incorrect password\n");
                 if(i==0)
                     goto exit;
                 goto read;
        }
        exit:                  i=0;
}

        
void trans(int n)
{
      int i=4,k;
      int c,a,j;
      int h,z,w;
      FILE *fp;
      char ch[30];
      char ch1[30];
      char str1[30];
      char str2[30];
      printf("Enter your account  name:\n");
      scanf("%s",ch);
      printf("Enter your account number as acc:\n");
      scanf("%s",str1);
read:      printf("Enter the password\n");
      scanf("%d",&k);
     if(k==1)
     {
                printf("Enter the account holder name to transfer:\n");
                scanf("%s",ch1);
                printf("Enter the account number to transfer as acc:\n");
                scanf("%s",str2);
                printf("Enter the amount to transfer:\n");
                scanf("%d",&h);

                fp=fopen(ch,"r");
                fscanf(fp,"%d",&c);
                fclose(fp);

                z=c-h;
                if(z>0)
                {
                        fp=fopen(ch,"w");
                        fprintf(fp,"%d",z);
                        fclose(fp);
                        printf("\n   Amount has been transfered\n");
                        printf("The balance is  ");
                        fp=fopen(ch,"r");
                        while((z=fgetc(fp))!=EOF)
                        putchar(z);
                        fclose(fp);
                        fp=fopen(str1,"a");
                        fprintf(fp,"%s to %s is %d at %s on %s",ch,ch1,h,__TIME__,__DATE__);
                        fclose(fp);

                }
                                   else
                {
                        printf("insufficient balance!");
                }

                FILE *q;

                q=fopen(ch1,"r");
                fscanf(q,"%d",&a);
                fclose(q);

                w=a+h;

                q=fopen(ch1,"w");
                fprintf(q,"%d",w);
                fclose(q);

                q=fopen(str2,"a");
                fprintf(q,"from %s to %s is %d at %s on %s",ch,ch1,h,__TIME__,__DATE__);
                fclose(q);


        }
        else
        {
                --i;
                printf("Incorrect password\n");
                if(i==0)
                                goto exit;
                goto read;
        }
        exit:     i=0;
}


void create(int n)
{
           int c=0;
           FILE *fp;
           char str1[30];
           char ch;
           int *a,k,m;
           struct details d[20];

               printf("Enter your name:\n");
               scanf("%s",str1);
      d[11].accno=11;
        d[11].amount=100000;
      printf("Enter your phone number:\n");
     scanf("%d",&a);
 read:     printf("create your password:\n");
      scanf("%d",&k);
      printf("please re-enter your password:\n");
       scanf("%d",&m);
        fp=fopen(str1,"w");
                fprintf(fp,"%d",c);
        fclose(fp);

        if(m==k)
        {
              printf("your account has been created");
        }
        else
        {
                 printf("wrong password\n");
                 goto read;
        }
}


 void history()
{
        int n,i=4;
        char ch;
        FILE *fp;
        char str1[30];
        printf("Enter the acc number as acc :\n");
        scanf("%s",str1);
        read:   printf("Enter the password :\n");
        scanf("%d",&n);
        if(n==1)
        {
                fp=fopen(str1,"r");
                while((ch=fgetc(fp))!=EOF)
                {
                        putchar(ch);
                }
                fclose(fp);
        }
        else
        {
                --i;
                 printf("incorrect password %d chances remaining\n",i);
                 if(i==0)
                        goto exit;
                goto read;
     }
        exit:  i=0;
}
