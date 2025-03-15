import { IApiassistencia, NewApiassistencia } from './apiassistencia.model';

export const sampleWithRequiredData: IApiassistencia = {
  id: 10278,
};

export const sampleWithPartialData: IApiassistencia = {
  id: 17539,
  filtro: '../fake-data/blob/hipster.txt',
  dadosassistencia: '../fake-data/blob/hipster.txt',
  chave: 'athwart chatter bright',
};

export const sampleWithFullData: IApiassistencia = {
  id: 31745,
  filtro: '../fake-data/blob/hipster.txt',
  dadosassistencia: '../fake-data/blob/hipster.txt',
  chave: 'medium fake busily',
};

export const sampleWithNewData: NewApiassistencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
