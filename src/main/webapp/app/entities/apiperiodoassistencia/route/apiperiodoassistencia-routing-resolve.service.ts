import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApiperiodoassistencia } from '../apiperiodoassistencia.model';
import { ApiperiodoassistenciaService } from '../service/apiperiodoassistencia.service';

const apiperiodoassistenciaResolve = (route: ActivatedRouteSnapshot): Observable<null | IApiperiodoassistencia> => {
  const id = route.params.id;
  if (id) {
    return inject(ApiperiodoassistenciaService)
      .find(id)
      .pipe(
        mergeMap((apiperiodoassistencia: HttpResponse<IApiperiodoassistencia>) => {
          if (apiperiodoassistencia.body) {
            return of(apiperiodoassistencia.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default apiperiodoassistenciaResolve;
