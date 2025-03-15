import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutenticacao } from '../autenticacao.model';
import { AutenticacaoService } from '../service/autenticacao.service';
import { AutenticacaoFormGroup, AutenticacaoFormService } from './autenticacao-form.service';

@Component({
  selector: 'jhi-autenticacao-update',
  templateUrl: './autenticacao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutenticacaoUpdateComponent implements OnInit {
  isSaving = false;
  autenticacao: IAutenticacao | null = null;

  protected autenticacaoService = inject(AutenticacaoService);
  protected autenticacaoFormService = inject(AutenticacaoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutenticacaoFormGroup = this.autenticacaoFormService.createAutenticacaoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autenticacao }) => {
      this.autenticacao = autenticacao;
      if (autenticacao) {
        this.updateForm(autenticacao);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autenticacao = this.autenticacaoFormService.getAutenticacao(this.editForm);
    if (autenticacao.id !== null) {
      this.subscribeToSaveResponse(this.autenticacaoService.update(autenticacao));
    } else {
      this.subscribeToSaveResponse(this.autenticacaoService.create(autenticacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutenticacao>>): void {
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

  protected updateForm(autenticacao: IAutenticacao): void {
    this.autenticacao = autenticacao;
    this.autenticacaoFormService.resetForm(this.editForm, autenticacao);
  }
}
