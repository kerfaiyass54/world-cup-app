import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WcInfos } from './wc-infos';

describe('WcInfos', () => {
  let component: WcInfos;
  let fixture: ComponentFixture<WcInfos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WcInfos],
    }).compileComponents();

    fixture = TestBed.createComponent(WcInfos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
