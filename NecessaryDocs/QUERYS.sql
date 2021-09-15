SELECT  
    COUNT(ID_CASO) FROM DATOS_COVID WHERE 
    SEXO IN(1, 2, 3) AND 
    ENTIDAD_RES IN(SELECT ID_ENTIDAD FROM ENTIDADES) AND 
    DEFUNCION IN(1, 2) AND 
    DIABETES IN(SELECT ID_SN FROM SI_NO) AND
    EPOC IN(SELECT ID_SN FROM SI_NO) AND
    ASMA IN(SELECT ID_SN FROM SI_NO) AND
    HIPERTENSION IN(SELECT ID_SN FROM SI_NO) AND
    CARDIOVASCULAR IN(SELECT ID_SN FROM SI_NO) AND
    OBESIDAD IN(SELECT ID_SN FROM SI_NO) AND
    TABAQUISMO IN(SELECT ID_SN FROM SI_NO) AND
    CLASIFICACION IN(SELECT ID_CLASIFICACION FROM CLASIFICACION)
    GROUP BY DEFUNCION;

SELECT 
    SUM(CASE WHEN EDAD BETWEEN 0 AND 10 THEN 1 ELSE 0 END) AS "0-10",
    SUM(CASE WHEN EDAD BETWEEN 11 AND 20 THEN 1 ELSE 0 END) AS "11-20",
    SUM(CASE WHEN EDAD BETWEEN 21 AND 30 THEN 1 ELSE 0 END) AS "21-30",
    SUM(CASE WHEN EDAD BETWEEN 31 AND 40 THEN 1 ELSE 0 END) AS "31-40",
    SUM(CASE WHEN EDAD BETWEEN 41 AND 50 THEN 1 ELSE 0 END) AS "41-50",
    SUM(CASE WHEN EDAD BETWEEN 51 AND 60 THEN 1 ELSE 0 END) AS "51-60",
    SUM(CASE WHEN EDAD BETWEEN 61 AND 70 THEN 1 ELSE 0 END) AS "61-70",
    SUM(CASE WHEN EDAD BETWEEN 71 AND 80 THEN 1 ELSE 0 END) AS "71-80",
    SUM(CASE WHEN EDAD BETWEEN 81 AND 90 THEN 1 ELSE 0 END) AS "81-90",
    SUM(CASE WHEN EDAD BETWEEN 91 AND 100 THEN 1 ELSE 0 END) AS "91-100",
    SUM(CASE WHEN EDAD BETWEEN 101 AND 120 THEN 1 ELSE 0 END) AS "101-110",
    SUM(CASE WHEN EDAD BETWEEN 111 AND 120 THEN 1 ELSE 0 END) AS "111-120"
    FROM DATOS_COVID WHERE 
    SEXO IN(1, 2, 3) AND 
    ENTIDAD_RES IN(SELECT ID_ENTIDAD FROM ENTIDADES) AND 
    DEFUNCION IN(1, 2) AND 
    DIABETES IN(SELECT ID_SN FROM SI_NO) AND
    EPOC IN(SELECT ID_SN FROM SI_NO) AND
    ASMA IN(SELECT ID_SN FROM SI_NO) AND
    HIPERTENSION IN(SELECT ID_SN FROM SI_NO) AND
    CARDIOVASCULAR IN(SELECT ID_SN FROM SI_NO) AND
    OBESIDAD IN(SELECT ID_SN FROM SI_NO) AND
    TABAQUISMO IN(SELECT ID_SN FROM SI_NO) AND
    CLASIFICACION IN(SELECT ID_CLASIFICACION FROM CLASIFICACION)
    GROUP BY DEFUNCION;