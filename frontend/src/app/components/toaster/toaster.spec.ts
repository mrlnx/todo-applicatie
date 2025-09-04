import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Toaster } from './toaster';
import { MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material/snack-bar';

describe('Toaster', () => {
  let component: Toaster;
  let fixture: ComponentFixture<Toaster>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Toaster],
      providers: [
        { provide: MatSnackBarRef, useValue: { dismiss: () => {} } },
        { provide: MAT_SNACK_BAR_DATA, useValue: { message: 'Test' } },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Toaster);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
