jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ApiperiodoassistenciaService } from '../service/apiperiodoassistencia.service';

import { ApiperiodoassistenciaDeleteDialogComponent } from './apiperiodoassistencia-delete-dialog.component';

describe('Apiperiodoassistencia Management Delete Component', () => {
  let comp: ApiperiodoassistenciaDeleteDialogComponent;
  let fixture: ComponentFixture<ApiperiodoassistenciaDeleteDialogComponent>;
  let service: ApiperiodoassistenciaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ApiperiodoassistenciaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ApiperiodoassistenciaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApiperiodoassistenciaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ApiperiodoassistenciaService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
