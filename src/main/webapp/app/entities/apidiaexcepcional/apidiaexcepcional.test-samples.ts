import { IApidiaexcepcional, NewApidiaexcepcional } from './apidiaexcepcional.model';

export const sampleWithRequiredData: IApidiaexcepcional = {
  id: 2122,
};

export const sampleWithPartialData: IApidiaexcepcional = {
  id: 10052,
  dadosdiaexcepcional: '../fake-data/blob/hipster.txt',
  chave: 'stall loudly',
};

export const sampleWithFullData: IApidiaexcepcional = {
  id: 11268,
  dadosdiaexcepcional: '../fake-data/blob/hipster.txt',
  chave: 'below',
};

export const sampleWithNewData: NewApidiaexcepcional = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
