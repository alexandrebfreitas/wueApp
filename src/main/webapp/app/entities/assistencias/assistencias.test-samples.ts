import { IAssistencias, NewAssistencias } from './assistencias.model';

export const sampleWithRequiredData: IAssistencias = {
  id: 30024,
};

export const sampleWithPartialData: IAssistencias = {
  id: 11031,
};

export const sampleWithFullData: IAssistencias = {
  id: 17161,
  value: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAssistencias = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
