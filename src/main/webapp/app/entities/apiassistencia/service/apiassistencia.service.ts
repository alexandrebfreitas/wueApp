import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApiassistencia, NewApiassistencia } from '../apiassistencia.model';

export type PartialUpdateApiassistencia = Partial<IApiassistencia> & Pick<IApiassistencia, 'id'>;

export type EntityResponseType = HttpResponse<IApiassistencia>;
export type EntityArrayResponseType = HttpResponse<IApiassistencia[]>;

@Injectable({ providedIn: 'root' })
export class ApiassistenciaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apiassistencias');

  create(apiassistencia: NewApiassistencia): Observable<EntityResponseType> {
    return this.http.post<IApiassistencia>(this.resourceUrl, apiassistencia, { observe: 'response' });
  }

  update(apiassistencia: IApiassistencia): Observable<EntityResponseType> {
    return this.http.put<IApiassistencia>(`${this.resourceUrl}/${this.getApiassistenciaIdentifier(apiassistencia)}`, apiassistencia, {
      observe: 'response',
    });
  }

  partialUpdate(apiassistencia: PartialUpdateApiassistencia): Observable<EntityResponseType> {
    return this.http.patch<IApiassistencia>(`${this.resourceUrl}/${this.getApiassistenciaIdentifier(apiassistencia)}`, apiassistencia, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiassistencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiassistencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApiassistenciaIdentifier(apiassistencia: Pick<IApiassistencia, 'id'>): number {
    return apiassistencia.id;
  }

  compareApiassistencia(o1: Pick<IApiassistencia, 'id'> | null, o2: Pick<IApiassistencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getApiassistenciaIdentifier(o1) === this.getApiassistenciaIdentifier(o2) : o1 === o2;
  }

  addApiassistenciaToCollectionIfMissing<Type extends Pick<IApiassistencia, 'id'>>(
    apiassistenciaCollection: Type[],
    ...apiassistenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const apiassistencias: Type[] = apiassistenciasToCheck.filter(isPresent);
    if (apiassistencias.length > 0) {
      const apiassistenciaCollectionIdentifiers = apiassistenciaCollection.map(apiassistenciaItem =>
        this.getApiassistenciaIdentifier(apiassistenciaItem),
      );
      const apiassistenciasToAdd = apiassistencias.filter(apiassistenciaItem => {
        const apiassistenciaIdentifier = this.getApiassistenciaIdentifier(apiassistenciaItem);
        if (apiassistenciaCollectionIdentifiers.includes(apiassistenciaIdentifier)) {
          return false;
        }
        apiassistenciaCollectionIdentifiers.push(apiassistenciaIdentifier);
        return true;
      });
      return [...apiassistenciasToAdd, ...apiassistenciaCollection];
    }
    return apiassistenciaCollection;
  }
}
