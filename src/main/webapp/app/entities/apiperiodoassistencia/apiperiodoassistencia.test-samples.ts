import { IApiperiodoassistencia, NewApiperiodoassistencia } from './apiperiodoassistencia.model';

export const sampleWithRequiredData: IApiperiodoassistencia = {
  id: 19212,
};

export const sampleWithPartialData: IApiperiodoassistencia = {
  id: 13215,
  dadosperiodo: '../fake-data/blob/hipster.txt',
  chave: 'conservative across lobster',
};

export const sampleWithFullData: IApiperiodoassistencia = {
  id: 18594,
  dadosperiodo: '../fake-data/blob/hipster.txt',
  chave: 'woot inconsequential times',
};

export const sampleWithNewData: NewApiperiodoassistencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
