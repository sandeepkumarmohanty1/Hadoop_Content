cricData = load '$inputFile'; 
pname = DISTINCT (FOREACH(FILTER cricData BY $1>49) GENERATE $0);
x = JOIN cricData BY $0, pname BY $0;
y = foreach x generate $0, $1, $2 ,ROUND((FLOAT)$1/$2*100) as sr;
res = foreach y generate $0, sr, (sr >= 50 and sr <= 100 ? 'Good' : (sr >= 100 ? 'Great' : 'AVG'));
STORE res INTO '$OutPut'; 

