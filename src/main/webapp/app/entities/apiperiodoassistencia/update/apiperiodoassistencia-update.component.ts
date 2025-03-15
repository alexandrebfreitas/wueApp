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
import { ApiperiodoassistenciaService } from '../service/apiperiodoassistencia.service';
import { IApiperiodoassistencia } from '../apiperiodoassistencia.model';
import { ApiperiodoassistenciaFormGroup, ApiperiodoassistenciaFormService } from './apiperiodoassistencia-form.service';

@Component({
  selector: 'jhi-apiperiodoassistencia-update',
  templateUrl: './apiperiodoassistencia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApiperiodoassistenciaUpdateComponent implements OnInit {
  isSaving = false;
  apiperiodoassistencia: IApiperiodoassistencia | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected apiperiodoassistenciaService = inject(ApiperiodoassistenciaService);
  protected apiperiodoassistenciaFormService = inject(ApiperiodoassistenciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ApiperiodoassistenciaFormGroup = this.apiperiodoassistenciaFormService.createApiperiodoassistenciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiperiodoassistencia }) => {
      this.apiperiodoassistencia = apiperiodoassistencia;
      if (apiperiodoassistencia) {
        this.updateForm(apiperiodoassistencia);
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
    const apiperiodoassistencia = this.apiperiodoassistenciaFormService.getApiperiodoassistencia(this.editForm);
    if (apiperiodoassistencia.id !== null) {
      this.subscribeToSaveResponse(this.apiperiodoassistenciaService.update(apiperiodoassistencia));
    } else {
      this.subscribeToSaveResponse(this.apiperiodoassistenciaService.create(apiperiodoassistencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiperiodoassistencia>>): void {
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

  protected updateForm(apiperiodoassistencia: IApiperiodoassistencia): void {
    this.apiperiodoassistencia = apiperiodoassistencia;
    this.apiperiodoassistenciaFormService.resetForm(this.editForm, apiperiodoassistencia);
  }
}
