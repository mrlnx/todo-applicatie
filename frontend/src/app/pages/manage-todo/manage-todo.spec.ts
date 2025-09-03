import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageTodo } from './manage-todo';

describe('ManageTodo', () => {
  let component: ManageTodo;
  let fixture: ComponentFixture<ManageTodo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageTodo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageTodo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
