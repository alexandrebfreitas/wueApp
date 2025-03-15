import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApidiaexcepcional, NewApidiaexcepcional } from '../apidiaexcepcional.model';

export type PartialUpdateApidiaexcepcional = Partial<IApidiaexcepcional> & Pick<IApidiaexcepcional, 'id'>;

export type EntityResponseType = HttpResponse<IApidiaexcepcional>;
export type EntityArrayResponseType = HttpResponse<IApidiaexcepcional[]>;

@Injectable({ providedIn: 'root' })
export class ApidiaexcepcionalService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apidiaexcepcionals');

  create(apidiaexcepcional: NewApidiaexcepcional): Observable<EntityResponseType> {
    return this.http.post<IApidiaexcepcional>(this.resourceUrl, apidiaexcepcional, { observe: 'response' });
  }

  update(apidiaexcepcional: IApidiaexcepcional): Observable<EntityResponseType> {
    return this.http.put<IApidiaexcepcional>(
      `${this.resourceUrl}/${this.getApidiaexcepcionalIdentifier(apidiaexcepcional)}`,
      apidiaexcepcional,
      { observe: 'response' },
    );
  }

  partialUpdate(apidiaexcepcional: PartialUpdateApidiaexcepcional): Observable<EntityResponseType> {
    return this.http.patch<IApidiaexcepcional>(
      `${this.resourceUrl}/${this.getApidiaexcepcionalIdentifier(apidiaexcepcional)}`,
      apidiaexcepcional,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApidiaexcepcional>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApidiaexcepcional[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApidiaexcepcionalIdentifier(apidiaexcepcional: Pick<IApidiaexcepcional, 'id'>): number {
    return apidiaexcepcional.id;
  }

  compareApidiaexcepcional(o1: Pick<IApidiaexcepcional, 'id'> | null, o2: Pick<IApidiaexcepcional, 'id'> | null): boolean {
    return o1 && o2 ? this.getApidiaexcepcionalIdentifier(o1) === this.getApidiaexcepcionalIdentifier(o2) : o1 === o2;
  }

  addApidiaexcepcionalToCollectionIfMissing<Type extends Pick<IApidiaexcepcional, 'id'>>(
    apidiaexcepcionalCollection: Type[],
    ...apidiaexcepcionalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const apidiaexcepcionals: Type[] = apidiaexcepcionalsToCheck.filter(isPresent);
    if (apidiaexcepcionals.length > 0) {
      const apidiaexcepcionalCollectionIdentifiers = apidiaexcepcionalCollection.map(apidiaexcepcionalItem =>
        this.getApidiaexcepcionalIdentifier(apidiaexcepcionalItem),
      );
      const apidiaexcepcionalsToAdd = apidiaexcepcionals.filter(apidiaexcepcionalItem => {
        const apidiaexcepcionalIdentifier = this.getApidiaexcepcionalIdentifier(apidiaexcepcionalItem);
        if (apidiaexcepcionalCollectionIdentifiers.includes(apidiaexcepcionalIdentifier)) {
          return false;
        }
        apidiaexcepcionalCollectionIdentifiers.push(apidiaexcepcionalIdentifier);
        return true;
      });
      return [...apidiaexcepcionalsToAdd, ...apidiaexcepcionalCollection];
    }
    return apidiaexcepcionalCollection;
  }
}
