import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IApiindicador } from '../apiindicador.model';
import { ApiindicadorService } from '../service/apiindicador.service';

@Component({
  templateUrl: './apiindicador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ApiindicadorDeleteDialogComponent {
  apiindicador?: IApiindicador;

  protected apiindicadorService = inject(ApiindicadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apiindicadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
