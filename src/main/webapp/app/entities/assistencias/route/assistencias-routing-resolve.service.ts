import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssistencias } from '../assistencias.model';
import { AssistenciasService } from '../service/assistencias.service';

const assistenciasResolve = (route: ActivatedRouteSnapshot): Observable<null | IAssistencias> => {
  const id = route.params.id;
  if (id) {
    return inject(AssistenciasService)
      .find(id)
      .pipe(
        mergeMap((assistencias: HttpResponse<IAssistencias>) => {
          if (assistencias.body) {
            return of(assistencias.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default assistenciasResolve;
