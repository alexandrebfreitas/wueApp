import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ApiassistenciaService } from '../service/apiassistencia.service';
import { IApiassistencia } from '../apiassistencia.model';
import { ApiassistenciaFormGroup, ApiassistenciaFormService } from './apiassistencia-form.service';

@Component({
  selector: 'jhi-apiassistencia-update',
  templateUrl: './apiassistencia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApiassistenciaUpdateComponent implements OnInit {
  isSaving = false;
  apiassistencia: IApiassistencia | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected apiassistenciaService = inject(ApiassistenciaService);
  protected apiassistenciaFormService = inject(ApiassistenciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ApiassistenciaFormGroup = this.apiassistenciaFormService.createApiassistenciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiassistencia }) => {
      this.apiassistencia = apiassistencia;
      if (apiassistencia) {
        this.updateForm(apiassistencia);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('wattsUpEnergyApp.error', { ...err, key: `error.file.${err.key}` })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apiassistencia = this.apiassistenciaFormService.getApiassistencia(this.editForm);
    if (apiassistencia.id !== null) {
      this.subscribeToSaveResponse(this.apiassistenciaService.update(apiassistencia));
    } else {
      this.subscribeToSaveResponse(this.apiassistenciaService.create(apiassistencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiassistencia>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(apiassistencia: IApiassistencia): void {
    this.apiassistencia = apiassistencia;
    this.apiassistenciaFormService.resetForm(this.editForm, apiassistencia);
  }
}
