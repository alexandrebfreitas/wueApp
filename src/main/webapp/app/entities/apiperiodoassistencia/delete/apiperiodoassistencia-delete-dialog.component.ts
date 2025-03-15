import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IApiperiodoassistencia } from '../apiperiodoassistencia.model';
import { ApiperiodoassistenciaService } from '../service/apiperiodoassistencia.service';

@Component({
  templateUrl: './apiperiodoassistencia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ApiperiodoassistenciaDeleteDialogComponent {
  apiperiodoassistencia?: IApiperiodoassistencia;

  protected apiperiodoassistenciaService = inject(ApiperiodoassistenciaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apiperiodoassistenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
