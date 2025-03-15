import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApiassistencia } from '../apiassistencia.model';
import { ApiassistenciaService } from '../service/apiassistencia.service';

const apiassistenciaResolve = (route: ActivatedRouteSnapshot): Observable<null | IApiassistencia> => {
  const id = route.params.id;
  if (id) {
    return inject(ApiassistenciaService)
      .find(id)
      .pipe(
        mergeMap((apiassistencia: HttpResponse<IApiassistencia>) => {
          if (apiassistencia.body) {
            return of(apiassistencia.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default apiassistenciaResolve;
