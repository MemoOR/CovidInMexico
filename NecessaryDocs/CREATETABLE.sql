CREATE TABLE DATOS_COVID 
(
  ID_CASO VARCHAR2(8) NOT NULL 
, ENTIDAD_RES NUMBER NOT NULL 
, SEXO NUMBER NOT NULL 
, DEFUNCION NUMBER NOT NULL 
, EDAD NUMBER NOT NULL 
, DIABETES NUMBER NOT NULL 
, EPOC NUMBER NOT NULL 
, ASMA NUMBER NOT NULL 
, HIPERTENSION NUMBER NOT NULL 
, CARDIOVASCULAR NUMBER NOT NULL 
, OBESIDAD NUMBER NOT NULL 
, TABAQUISMO NUMBER NOT NULL 
, CLASIFICACION NUMBER NOT NULL 
, PRIMARY KEY (ID_CASO)
, FOREIGN KEY (ENTIDAD_RES) REFERENCES ENTIDADES(ID_ENTIDAD)
, FOREIGN KEY (SEXO) REFERENCES SEXO(ID_SEXO)
, FOREIGN KEY (DEFUNCION) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (DIABETES) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (EPOC) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (ASMA) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (HIPERTENSION) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (CARDIOVASCULAR) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (OBESIDAD) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (TABAQUISMO) REFERENCES SI_NO(ID_SN)
, FOREIGN KEY (CLASIFICACION) REFERENCES CLASIFICACION(ID_CLASIFICACION)
);

drop table DATOS_COVID PURGE;