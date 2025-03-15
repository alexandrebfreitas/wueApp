import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApiindicador, NewApiindicador } from '../apiindicador.model';

export type PartialUpdateApiindicador = Partial<IApiindicador> & Pick<IApiindicador, 'id'>;

export type EntityResponseType = HttpResponse<IApiindicador>;
export type EntityArrayResponseType = HttpResponse<IApiindicador[]>;

@Injectable({ providedIn: 'root' })
export class ApiindicadorService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apiindicadors');

  create(apiindicador: NewApiindicador): Observable<EntityResponseType> {
    return this.http.post<IApiindicador>(this.resourceUrl, apiindicador, { observe: 'response' });
  }

  update(apiindicador: IApiindicador): Observable<EntityResponseType> {
    return this.http.put<IApiindicador>(`${this.resourceUrl}/${this.getApiindicadorIdentifier(apiindicador)}`, apiindicador, {
      observe: 'response',
    });
  }

  partialUpdate(apiindicador: PartialUpdateApiindicador): Observable<EntityResponseType> {
    return this.http.patch<IApiindicador>(`${this.resourceUrl}/${this.getApiindicadorIdentifier(apiindicador)}`, apiindicador, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiindicador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiindicador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApiindicadorIdentifier(apiindicador: Pick<IApiindicador, 'id'>): number {
    return apiindicador.id;
  }

  compareApiindicador(o1: Pick<IApiindicador, 'id'> | null, o2: Pick<IApiindicador, 'id'> | null): boolean {
    return o1 && o2 ? this.getApiindicadorIdentifier(o1) === this.getApiindicadorIdentifier(o2) : o1 === o2;
  }

  addApiindicadorToCollectionIfMissing<Type extends Pick<IApiindicador, 'id'>>(
    apiindicadorCollection: Type[],
    ...apiindicadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const apiindicadors: Type[] = apiindicadorsToCheck.filter(isPresent);
    if (apiindicadors.length > 0) {
      const apiindicadorCollectionIdentifiers = apiindicadorCollection.map(apiindicadorItem =>
        this.getApiindicadorIdentifier(apiindicadorItem),
      );
      const apiindicadorsToAdd = apiindicadors.filter(apiindicadorItem => {
        const apiindicadorIdentifier = this.getApiindicadorIdentifier(apiindicadorItem);
        if (apiindicadorCollectionIdentifiers.includes(apiindicadorIdentifier)) {
          return false;
        }
        apiindicadorCollectionIdentifiers.push(apiindicadorIdentifier);
        return true;
      });
      return [...apiindicadorsToAdd, ...apiindicadorCollection];
    }
    return apiindicadorCollection;
  }
}
