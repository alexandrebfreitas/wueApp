import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApiperiodoassistencia, NewApiperiodoassistencia } from '../apiperiodoassistencia.model';

export type PartialUpdateApiperiodoassistencia = Partial<IApiperiodoassistencia> & Pick<IApiperiodoassistencia, 'id'>;

export type EntityResponseType = HttpResponse<IApiperiodoassistencia>;
export type EntityArrayResponseType = HttpResponse<IApiperiodoassistencia[]>;

@Injectable({ providedIn: 'root' })
export class ApiperiodoassistenciaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apiperiodoassistencias');

  create(apiperiodoassistencia: NewApiperiodoassistencia): Observable<EntityResponseType> {
    return this.http.post<IApiperiodoassistencia>(this.resourceUrl, apiperiodoassistencia, { observe: 'response' });
  }

  update(apiperiodoassistencia: IApiperiodoassistencia): Observable<EntityResponseType> {
    return this.http.put<IApiperiodoassistencia>(
      `${this.resourceUrl}/${this.getApiperiodoassistenciaIdentifier(apiperiodoassistencia)}`,
      apiperiodoassistencia,
      { observe: 'response' },
    );
  }

  partialUpdate(apiperiodoassistencia: PartialUpdateApiperiodoassistencia): Observable<EntityResponseType> {
    return this.http.patch<IApiperiodoassistencia>(
      `${this.resourceUrl}/${this.getApiperiodoassistenciaIdentifier(apiperiodoassistencia)}`,
      apiperiodoassistencia,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiperiodoassistencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiperiodoassistencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApiperiodoassistenciaIdentifier(apiperiodoassistencia: Pick<IApiperiodoassistencia, 'id'>): number {
    return apiperiodoassistencia.id;
  }

  compareApiperiodoassistencia(o1: Pick<IApiperiodoassistencia, 'id'> | null, o2: Pick<IApiperiodoassistencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getApiperiodoassistenciaIdentifier(o1) === this.getApiperiodoassistenciaIdentifier(o2) : o1 === o2;
  }

  addApiperiodoassistenciaToCollectionIfMissing<Type extends Pick<IApiperiodoassistencia, 'id'>>(
    apiperiodoassistenciaCollection: Type[],
    ...apiperiodoassistenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const apiperiodoassistencias: Type[] = apiperiodoassistenciasToCheck.filter(isPresent);
    if (apiperiodoassistencias.length > 0) {
      const apiperiodoassistenciaCollectionIdentifiers = apiperiodoassistenciaCollection.map(apiperiodoassistenciaItem =>
        this.getApiperiodoassistenciaIdentifier(apiperiodoassistenciaItem),
      );
      const apiperiodoassistenciasToAdd = apiperiodoassistencias.filter(apiperiodoassistenciaItem => {
        const apiperiodoassistenciaIdentifier = this.getApiperiodoassistenciaIdentifier(apiperiodoassistenciaItem);
        if (apiperiodoassistenciaCollectionIdentifiers.includes(apiperiodoassistenciaIdentifier)) {
          return false;
        }
        apiperiodoassistenciaCollectionIdentifiers.push(apiperiodoassistenciaIdentifier);
        return true;
      });
      return [...apiperiodoassistenciasToAdd, ...apiperiodoassistenciaCollection];
    }
    return apiperiodoassistenciaCollection;
  }
}
