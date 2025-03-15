import { IApiindicador, NewApiindicador } from './apiindicador.model';

export const sampleWithRequiredData: IApiindicador = {
  id: 4101,
};

export const sampleWithPartialData: IApiindicador = {
  id: 20655,
  dadosindicador: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IApiindicador = {
  id: 10019,
  filtro: '../fake-data/blob/hipster.txt',
  dadosindicador: '../fake-data/blob/hipster.txt',
  chave: 'eek',
};

export const sampleWithNewData: NewApiindicador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
