import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssistencias, NewAssistencias } from '../assistencias.model';

export type PartialUpdateAssistencias = Partial<IAssistencias> & Pick<IAssistencias, 'id'>;

export type EntityResponseType = HttpResponse<IAssistencias>;
export type EntityArrayResponseType = HttpResponse<IAssistencias[]>;

@Injectable({ providedIn: 'root' })
export class AssistenciasService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/assistencias');

  create(assistencias: NewAssistencias): Observable<EntityResponseType> {
    return this.http.post<IAssistencias>(this.resourceUrl, assistencias, { observe: 'response' });
  }

  update(assistencias: IAssistencias): Observable<EntityResponseType> {
    return this.http.put<IAssistencias>(`${this.resourceUrl}/${this.getAssistenciasIdentifier(assistencias)}`, assistencias, {
      observe: 'response',
    });
  }

  partialUpdate(assistencias: PartialUpdateAssistencias): Observable<EntityResponseType> {
    return this.http.patch<IAssistencias>(`${this.resourceUrl}/${this.getAssistenciasIdentifier(assistencias)}`, assistencias, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssistencias>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssistencias[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssistenciasIdentifier(assistencias: Pick<IAssistencias, 'id'>): number {
    return assistencias.id;
  }

  compareAssistencias(o1: Pick<IAssistencias, 'id'> | null, o2: Pick<IAssistencias, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssistenciasIdentifier(o1) === this.getAssistenciasIdentifier(o2) : o1 === o2;
  }

  addAssistenciasToCollectionIfMissing<Type extends Pick<IAssistencias, 'id'>>(
    assistenciasCollection: Type[],
    ...assistenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const assistencias: Type[] = assistenciasToCheck.filter(isPresent);
    if (assistencias.length > 0) {
      const assistenciasCollectionIdentifiers = assistenciasCollection.map(assistenciasItem =>
        this.getAssistenciasIdentifier(assistenciasItem),
      );
      const assistenciasToAdd = assistencias.filter(assistenciasItem => {
        const assistenciasIdentifier = this.getAssistenciasIdentifier(assistenciasItem);
        if (assistenciasCollectionIdentifiers.includes(assistenciasIdentifier)) {
          return false;
        }
        assistenciasCollectionIdentifiers.push(assistenciasIdentifier);
        return true;
      });
      return [...assistenciasToAdd, ...assistenciasCollection];
    }
    return assistenciasCollection;
  }
}
