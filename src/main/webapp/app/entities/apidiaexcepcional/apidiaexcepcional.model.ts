export interface IApidiaexcepcional {
  id: number;
  dadosdiaexcepcional?: string | null;
  chave?: string | null;
}

export type NewApidiaexcepcional = Omit<IApidiaexcepcional, 'id'> & { id: null };
