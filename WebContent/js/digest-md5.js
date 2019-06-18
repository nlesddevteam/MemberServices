/*
The following JavaScript MD5 implementation is
Copyright © 2002 Centrinity Inc. All Rights Reserved
as a derivative work of the RSA Data Security, Inc. MD5 Message-Digest Algorigthm
Copyright © 1991-2, RSA Data Security, Inc. All Rights Reserved.
*/
var S11=7;
var S12=12;
var S13=17;
var S14=22;
var S21=5;
var S22=9;
var S23=14;
var S24=20;
var S31=4;
var S32=11;
var S33=16;
var S34=23;
var S41=6;
var S42=10;
var S43=15;
var S44=21;
var PAD=new Array(0x80,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
var S=new Array();
var CN=new Array();
var B=new Array();
var D=new Array();
var HD=new String("0123456789abcdef");

function AND(a,b){
var hb=(a>0x80000000)&&(b>0x80000000);
var r=0;
if(a>0x80000000)a-=0x80000000;
if(b>0x80000000)b-=0x80000000;
r=a&b;
if(hb)
r+=0x80000000;
return r;
}

function OR(a,b){
var hb=(a>0x80000000)||(b>0x80000000);
var r=0;
if(a>0x80000000)a-=0x80000000;
if(b>0x80000000)b-=0x80000000;
r=a|b;
if(hb)
r+=0x80000000;
return r;
}

function XOR(a,b){
var hb=((a>0x80000000)&&(b<0x80000000))||((a<0x80000000)&&(b>0x80000000));
var r=0;
if(a>0x80000000)a-=0x80000000;
if(b>0x80000000)b-=0x80000000;
r=a^b;
if(hb)
r+=0x80000000;
return r;
}

function NOT(a){
var b=0x80000000;
var n=0;
while(b>=1){
if(a>=b)
	a-=b;
else
	n+=b;
b=b/2;
}
return n;
}

function ADD(a,b){
var n=0;
if(a>0x80000000){
++n;
a-=0x80000000;
}
if(b>0x80000000){
++n;
b-=0x80000000;
}
a+=b;
if(n==1){
	if(a>0x80000000)
		a-=0x80000000;
	else
		a+=0x80000000;
}
return a;
}

function LS(a,s){
for(var i=0;i<s;i++){
if(a>0x80000000)
	a-=0x80000000;
a=a*2;
}
return a;
}

function RS(a,s){
for(var i=0;i<s;i++){
if(a%2)
	a-=1;
a=a/2;
}
return a;
}

function HEX1(c){return HD.substr((c>>4)&0x0F,1);}

function HEX2(c){return HD.substr(c&0x0F,1);}

function FF(a,b,c,d,x,s,ac){
var da=false;
a=ADD(a,OR(AND(b,c),AND(NOT(b),d)));
a=ADD(a,ac);
a=ADD(a,x);

a = OR(LS(a,s),RS(a,32-s));
a=ADD(a,b);
return a;
}

function GG(a,b,c,d,x,s,ac){
a=ADD(a,OR(AND(b,d),AND(c,NOT(d))));
a=ADD(a,ac);
a=ADD(a,x);

a = OR(LS(a,s),RS(a,32-s));
a=ADD(a,b);
return a;
}

function HH(a,b,c,d,x,s,ac){
a=ADD(a,XOR(d,XOR(b,c)));
a=ADD(a,ac);
a=ADD(a,x);

a = OR(LS(a,s),RS(a,32-s));
a=ADD(a,b);
return a;
}

function II(a,b,c,d,x,s,ac){
a=ADD(a,XOR(c,OR(b,NOT(d))));
a=ADD(a,ac);
a=ADD(a,x);

a = OR(LS(a,s),RS(a,32-s));
a=ADD(a,b);
return a;
}

function EC(I,L,O){
var i, j;
for(i=0,j=0;j<L;i++,j+=4){
	O[j]=AND(I[i],0xFF);
	O[j+1]=AND(RS(I[i],8),0xFF);
	O[j+2]=AND(RS(I[i],16),0xFF);
	O[j+3]=AND(RS(I[i],24),0xFF);
}
}

function DC(I,L,O){
var i, j;
for (i=0,j=0;j<L;i++,j+=4){
	O[i]=0;
	O[i]=ADD(O[i],I[j]);
	O[i]=ADD(O[i],LS(I[j+1],8));
	O[i]=ADD(O[i],LS(I[j+2],16));
	O[i]=ADD(O[i],LS(I[j+3],24));
}
}

function TF(bl){
var a=S[0],b=S[1],c=S[2],d=S[3];
var x=new Array();
DC(bl,64,x);

a=FF(a,b,c,d,x[0],S11,0xd76aa478);
d=FF(d,a,b,c,x[1],S12,0xe8c7b756);
c=FF(c,d,a,b,x[2],S13,0x242070db);
b=FF(b,c,d,a,x[3],S14,0xc1bdceee);
a=FF(a,b,c,d,x[4],S11,0xf57c0faf);
d=FF(d,a,b,c,x[5],S12,0x4787c62a);
c=FF(c,d,a,b,x[6],S13,0xa8304613);
b=FF(b,c,d,a,x[7],S14,0xfd469501);
a=FF(a,b,c,d,x[8],S11,0x698098d8);
d=FF(d,a,b,c,x[9],S12,0x8b44f7af);
c=FF(c,d,a,b,x[10],S13,0xffff5bb1);
b=FF(b,c,d,a,x[11],S14,0x895cd7be);
a=FF(a,b,c,d,x[12],S11,0x6b901122);
d=FF(d,a,b,c,x[13],S12,0xfd987193);
c=FF(c,d,a,b,x[14],S13,0xa679438e);
b=FF(b,c,d,a,x[15],S14,0x49b40821);

a=GG(a,b,c,d,x[1],S21,0xf61e2562);
d=GG(d,a,b,c,x[6],S22,0xc040b340);
c=GG(c,d,a,b,x[11],S23,0x265e5a51);
b=GG(b,c,d,a,x[0],S24,0xe9b6c7aa);
a=GG(a,b,c,d,x[5],S21,0xd62f105d);
d=GG(d,a,b,c,x[10],S22,0x2441453);
c=GG(c,d,a,b,x[15],S23,0xd8a1e681);
b=GG(b,c,d,a,x[4],S24,0xe7d3fbc8);
a=GG(a,b,c,d,x[9],S21,0x21e1cde6);
d=GG(d,a,b,c,x[14],S22,0xc33707d6);
c=GG(c,d,a,b,x[3],S23,0xf4d50d87);
b=GG(b,c,d,a,x[8],S24,0x455a14ed);
a=GG(a,b,c,d,x[13],S21,0xa9e3e905);
d=GG(d,a,b,c,x[2],S22,0xfcefa3f8);
c=GG(c,d,a,b,x[7],S23,0x676f02d9);
b=GG(b,c,d,a,x[12],S24,0x8d2a4c8a);

a=HH(a,b,c,d,x[5],S31,0xfffa3942);
d=HH(d,a,b,c,x[8],S32,0x8771f681);
c=HH(c,d,a,b,x[11],S33,0x6d9d6122);
b=HH(b,c,d,a,x[14],S34,0xfde5380c);
a=HH(a,b,c,d,x[1],S31,0xa4beea44);
d=HH(d,a,b,c,x[4],S32,0x4bdecfa9);
c=HH(c,d,a,b,x[7],S33,0xf6bb4b60);
b=HH(b,c,d,a,x[10],S34,0xbebfbc70);
a=HH(a,b,c,d,x[13],S31,0x289b7ec6);
d=HH(d,a,b,c,x[0],S32,0xeaa127fa);
c=HH(c,d,a,b,x[3],S33,0xd4ef3085);
b=HH(b,c,d,a,x[6],S34,0x4881d05);
a=HH(a,b,c,d,x[9],S31,0xd9d4d039);
d=HH(d,a,b,c,x[12],S32,0xe6db99e5);
c=HH(c,d,a,b,x[15],S33,0x1fa27cf8);
b=HH(b,c,d,a,x[2],S34,0xc4ac5665);

a=II(a,b,c,d,x[ 0],S41,0xf4292244);
d=II(d,a,b,c,x[ 7],S42,0x432aff97);
c=II(c,d,a,b,x[14],S43,0xab9423a7);
b=II(b,c,d,a,x[ 5],S44,0xfc93a039);
a=II(a,b,c,d,x[12],S41,0x655b59c3);
d=II(d,a,b,c,x[ 3],S42,0x8f0ccc92);
c=II(c,d,a,b,x[10],S43,0xffeff47d);
b=II(b,c,d,a,x[ 1],S44,0x85845dd1);
a=II(a,b,c,d,x[ 8],S41,0x6fa87e4f);
d=II(d,a,b,c,x[15],S42,0xfe2ce6e0);
c=II(c,d,a,b,x[ 6],S43,0xa3014314);
b=II(b,c,d,a,x[13],S44,0x4e0811a1);
a=II(a,b,c,d,x[ 4],S41,0xf7537e82);
d=II(d,a,b,c,x[11],S42,0xbd3af235);
c=II(c,d,a,b,x[ 2],S43,0x2ad7d2bb);
b=II(b,c,d,a,x[ 9],S44,0xeb86d391);
S[0]=ADD(S[0],a);
S[1]=ADD(S[1],b);
S[2]=ADD(S[2],c);
S[3]=ADD(S[3],d);
}

function CA(d,di,s,si,L){
for(i=0;i<L;i++)
	d[di+i]=s[si+i];
}

function UD(I,L){
var i,n,pl;
var T=new Array();

n=AND(RS(CN[0],3),0x3F);

if ((CN[0]+=LS(L,3))<LS(L,3))
	CN[1]++;
CN[1]+=RS(L,29);
pl=64-n;

if(L>=pl){
	CA(B,n,I,0,pl);
	TF(B);
	for(i=pl;i+63<L;i+=64){
		CA(T,0,I,i);
		TF(T);
	}
	n=0;
}else
	i=0;

CA(B,n,I,i,L-i);
}

function F(){
var b=new Array();
var i,p;
var R="";

EC(CN,8,b);

i=((CN[0]>>3)&0x3F);
p=(i<56)?(56-i):(120-i);
UD(PAD,p);

UD(b,8);

EC(S,16,D);
for(i=0;i<16;i++){
	R+=String(HEX1(D[i]));
	R+=String(HEX2(D[i]));
}
return R;
}

function MD5(C,P){
var c=new Array()
var p=new Array();
var i;
for(i=0;i<C.length;i++)
	c[i]=C.charCodeAt(i);
for (i=0;i<P.length;i++)
	p[i]=P.charCodeAt(i);
CN[0]=0;
CN[1]=0;

S[0]=0x67452301;
S[1]=0xEFCDAB89;
S[2]=0x98BADCFE;
S[3]=0x10325476;
UD(c,c.length);
UD(p,p.length);
return F();
}

function CMD5(F){
var p=self.sp;
if(p==null)
	p=F.pass.value;
if (F.user.length==0||F.pass.length==0)
	return false;
F.md5.value=MD5(F.md5challenge.value,p);
//F.pass.value="";
return true;
}

var sp=null;