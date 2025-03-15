import { Component, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DataUtils } from 'app/core/util/data-util.service';
import { IApiperiodoassistencia } from '../apiperiodoassistencia.model';

@Component({
  selector: 'jhi-apiperiodoassistencia-detail',
  templateUrl: './apiperiodoassistencia-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ApiperiodoassistenciaDetailComponent {
  apiperiodoassistencia = input<IApiperiodoassistencia | null>(null);

  protected dataUtils = inject(DataUtils);

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
