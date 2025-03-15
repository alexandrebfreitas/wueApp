import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAutenticacao, NewAutenticacao } from '../autenticacao.model';

export type PartialUpdateAutenticacao = Partial<IAutenticacao> & Pick<IAutenticacao, 'id'>;

export type EntityResponseType = HttpResponse<IAutenticacao>;
export type EntityArrayResponseType = HttpResponse<IAutenticacao[]>;

@Injectable({ providedIn: 'root' })
export class AutenticacaoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autenticacaos');

  create(autenticacao: NewAutenticacao): Observable<EntityResponseType> {
    return this.http.post<IAutenticacao>(this.resourceUrl, autenticacao, { observe: 'response' });
  }

  update(autenticacao: IAutenticacao): Observable<EntityResponseType> {
    return this.http.put<IAutenticacao>(`${this.resourceUrl}/${this.getAutenticacaoIdentifier(autenticacao)}`, autenticacao, {
      observe: 'response',
    });
  }

  partialUpdate(autenticacao: PartialUpdateAutenticacao): Observable<EntityResponseType> {
    return this.http.patch<IAutenticacao>(`${this.resourceUrl}/${this.getAutenticacaoIdentifier(autenticacao)}`, autenticacao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutenticacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutenticacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutenticacaoIdentifier(autenticacao: Pick<IAutenticacao, 'id'>): number {
    return autenticacao.id;
  }

  compareAutenticacao(o1: Pick<IAutenticacao, 'id'> | null, o2: Pick<IAutenticacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getAutenticacaoIdentifier(o1) === this.getAutenticacaoIdentifier(o2) : o1 === o2;
  }

  addAutenticacaoToCollectionIfMissing<Type extends Pick<IAutenticacao, 'id'>>(
    autenticacaoCollection: Type[],
    ...autenticacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autenticacaos: Type[] = autenticacaosToCheck.filter(isPresent);
    if (autenticacaos.length > 0) {
      const autenticacaoCollectionIdentifiers = autenticacaoCollection.map(autenticacaoItem =>
        this.getAutenticacaoIdentifier(autenticacaoItem),
      );
      const autenticacaosToAdd = autenticacaos.filter(autenticacaoItem => {
        const autenticacaoIdentifier = this.getAutenticacaoIdentifier(autenticacaoItem);
        if (autenticacaoCollectionIdentifiers.includes(autenticacaoIdentifier)) {
          return false;
        }
        autenticacaoCollectionIdentifiers.push(autenticacaoIdentifier);
        return true;
      });
      return [...autenticacaosToAdd, ...autenticacaoCollection];
    }
    return autenticacaoCollection;
  }
}
