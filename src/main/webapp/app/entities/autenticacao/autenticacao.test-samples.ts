import { IAutenticacao, NewAutenticacao } from './autenticacao.model';

export const sampleWithRequiredData: IAutenticacao = {
  id: 27885,
};

export const sampleWithPartialData: IAutenticacao = {
  id: 3975,
  usuario: 'schnitzel',
  senha: 'ack bookcase kinase',
  accessToken: 'because instead fess',
  tokenType: 'rationalise immaculate snowplow',
};

export const sampleWithFullData: IAutenticacao = {
  id: 27183,
  usuario: 'midst',
  senha: 'not diligent ouch',
  accessToken: 'because',
  tokenType: 'indeed yuck trust',
};

export const sampleWithNewData: NewAutenticacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
