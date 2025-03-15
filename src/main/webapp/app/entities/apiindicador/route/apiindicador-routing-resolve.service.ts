import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApiindicador } from '../apiindicador.model';
import { ApiindicadorService } from '../service/apiindicador.service';

const apiindicadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IApiindicador> => {
  const id = route.params.id;
  if (id) {
    return inject(ApiindicadorService)
      .find(id)
      .pipe(
        mergeMap((apiindicador: HttpResponse<IApiindicador>) => {
          if (apiindicador.body) {
            return of(apiindicador.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default apiindicadorResolve;
