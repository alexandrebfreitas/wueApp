export interface IAutenticacao {
  id: number;
  usuario?: string | null;
  senha?: string | null;
  accessToken?: string | null;
  tokenType?: string | null;
}

export type NewAutenticacao = Omit<IAutenticacao, 'id'> & { id: null };
