/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.infinity.jerry.dearmacfirst.R;
import com.infinity.jerry.yyd_tms.base.BaseActivity;
import com.infinity.jerry.yyd_tms.ui.customview.ZTitlebar;
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs;

/**
 * Created by jerry on 2018/8/3.
 * You are good enough to do everthing
 */
public class BillSettingActivity extends BaseActivity {

    private ZTitlebar titlebar;
    private Spinner spPay;
    private Spinner spMethod;
    private Spinner spPriceDetail;
    private Spinner spSpace;
    private Spinner spHead;
    private Spinner spSize;
    private Spinner spBarcode;
    private Spinner spTimeArr;

    private Spinner spSend;
    private Spinner spRecice;
    private Spinner spNumber;

    private Spinner spPos;

    private Spinner spBigNumber;

    private Spinner spBigDianFu;

    private String payMethod;
    private String deliMethod;
    private int isPriceOn = 0;
    private int spaceCount = 0;
    private int specialHint = 0;
    private int priceSize = 0;
    private int isBarcode = 1;
    private int timeDis = 0;

    private int sendSize = 0;
    private int receiveSize = 0;
    private int numberSize = 0;

    private int isFeight = 0;
    private int isTotal = 0;

    private int posNumber = 0;
    private int bigNumber = 0;
    private int bigDianFu = 0;

    @Override
    public void initData() {
        payMethod = ZShrPrefencs.getInstance().getPayMethod();
        deliMethod = ZShrPrefencs.getInstance().getDeliMethod();
        isPriceOn = ZShrPrefencs.getInstance().getPriceDetail();
        spaceCount = ZShrPrefencs.getInstance().getSpaceCount();
        specialHint = ZShrPrefencs.getInstance().getHintParams();
        priceSize = ZShrPrefencs.getInstance().getPriceSize();
        isBarcode = ZShrPrefencs.getInstance().getIsBarCode();
        timeDis = ZShrPrefencs.getInstance().getTimeDistr();
        sendSize = ZShrPrefencs.getInstance().getSendSize();
        receiveSize = ZShrPrefencs.getInstance().getReciveSize();
        numberSize = ZShrPrefencs.getInstance().getNumberSize();
        posNumber = ZShrPrefencs.getInstance().getPosMax();
        bigNumber = ZShrPrefencs.getInstance().getBigNumber();
        bigDianFu = ZShrPrefencs.getInstance().getBigDianFu();
    }

    @Override
    public int getLayoutId() {
        SetStatusBarColor();
        return R.layout.activity_billing_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titlebar = (ZTitlebar) findViewById(R.id.titleBar);
        spPay = (Spinner) findViewById(R.id.spPay);
        spMethod = (Spinner) findViewById(R.id.spDeli);
        spPriceDetail = (Spinner) findViewById(R.id.spPriceDetail);
        spSpace = (Spinner) findViewById(R.id.spSpace);
        spHead = (Spinner) findViewById(R.id.spHead);
        spSize = (Spinner) findViewById(R.id.spSize);
        spBarcode = (Spinner) findViewById(R.id.spBarcode);
        spTimeArr = (Spinner) findViewById(R.id.spTimeArr);
        spSend = (Spinner) findViewById(R.id.spSend);
        spRecice = (Spinner) findViewById(R.id.spRecive);
        spNumber = (Spinner) findViewById(R.id.spNumber);
        spPos = (Spinner) findViewById(R.id.spPos);
        spBigNumber = (Spinner) findViewById(R.id.spBigNumber);
        spBigDianFu = (Spinner) findViewById(R.id.spBigDianFu);

        titlebar.setTitle("开票设置");

        if (payMethod.equals("现付")) {
            spPay.setSelection(0);
        } else if (payMethod.equals("到付")) {
            spPay.setSelection(1);
        }

        if (deliMethod.equals("自提")) {
            spMethod.setSelection(0);
        } else if (deliMethod.equals("送货")) {
            spMethod.setSelection(1);
        }
        if (isPriceOn == 0) {
            spPriceDetail.setSelection(0);
        } else {
            spPriceDetail.setSelection(1);
        }

        if (priceSize == 0) {
            spSize.setSelection(0);
        } else {
            spSize.setSelection(1);
        }

        spSpace.setSelection(spaceCount);

        if (specialHint == 0) {
            spHead.setSelection(0);
        } else {
            spHead.setSelection(1);
        }

        if (isBarcode == 1) {
            spBarcode.setSelection(1);
        } else {
            spBarcode.setSelection(0);
        }

        if (timeDis == 0) {
            spTimeArr.setSelection(0);
        } else if (timeDis == 1100) {
            spTimeArr.setSelection(1);
        } else if (timeDis == 1130) {
            spTimeArr.setSelection(2);
        } else if (timeDis == 1200) {
            spTimeArr.setSelection(3);
        } else if (timeDis == 1230) {
            spTimeArr.setSelection(4);
        } else if (timeDis == 1300) {
            spTimeArr.setSelection(5);
        } else if (timeDis == 1330) {
            spTimeArr.setSelection(6);
        } else if (timeDis == 1400) {
            spTimeArr.setSelection(7);
        } else if (timeDis == 1430) {
            spTimeArr.setSelection(8);
        } else if (timeDis == 1500) {
            spTimeArr.setSelection(9);
        }

        if (sendSize == 0) {
            spSend.setSelection(0);
        } else {
            spSend.setSelection(1);
        }
        if (receiveSize == 0) {
            spRecice.setSelection(0);
        } else {
            spRecice.setSelection(1);
        }
        if (numberSize == 0) {
            spNumber.setSelection(0);
        } else {
            spNumber.setSelection(1);
        }

        if (posNumber == 0) {
            spPos.setSelection(0);
        } else if (posNumber == 5) {
            spPos.setSelection(1);
        } else if (posNumber == 10) {
            spPos.setSelection(2);
        } else if (posNumber == 15) {
            spPos.setSelection(3);
        } else if (posNumber == 20) {
            spPos.setSelection(4);
        } else if (posNumber == 25) {
            spPos.setSelection(5);
        } else if (posNumber == 30) {
            spPos.setSelection(6);
        }

        if (bigNumber == 0) {
            spBigNumber.setSelection(0);
        } else {
            spBigNumber.setSelection(1);
        }

        if (bigDianFu == 0) {
            spBigDianFu.setSelection(0);
        } else {
            spBigDianFu.setSelection(1);
        }

        spPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setPayMethod("现付");
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setPayMethod("到付");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setDeliMethod("自提");
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setDeliMethod("送货");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPriceDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setIsPriceDetail(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setIsPriceDetail(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSpace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ZShrPrefencs.getInstance().setSpaceCount(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spHead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setHintParams(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setHintParams(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setPriceSize(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setPriceSize(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setIsBarCode(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setIsBarCode(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTimeArr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setTimeDistr(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setTimeDistr(1100);
                        break;
                    case 2:
                        ZShrPrefencs.getInstance().setTimeDistr(1130);
                        break;
                    case 3:
                        ZShrPrefencs.getInstance().setTimeDistr(1200);
                        break;
                    case 4:
                        ZShrPrefencs.getInstance().setTimeDistr(1230);
                        break;
                    case 5:
                        ZShrPrefencs.getInstance().setTimeDistr(1300);
                        break;
                    case 6:
                        ZShrPrefencs.getInstance().setTimeDistr(1330);
                        break;
                    case 7:
                        ZShrPrefencs.getInstance().setTimeDistr(1400);
                        break;
                    case 8:
                        ZShrPrefencs.getInstance().setTimeDistr(1430);
                        break;
                    case 9:
                        ZShrPrefencs.getInstance().setTimeDistr(1500);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setSendSize(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setSendSize(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spRecice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setReciveSize(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setReciveSize(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setNumberSize(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setNumberSize(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setPosMax(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setPosMax(5);
                        break;
                    case 2:
                        ZShrPrefencs.getInstance().setPosMax(10);
                        break;
                    case 3:
                        ZShrPrefencs.getInstance().setPosMax(15);
                        break;
                    case 4:
                        ZShrPrefencs.getInstance().setPosMax(20);
                        break;
                    case 5:
                        ZShrPrefencs.getInstance().setPosMax(25);
                        break;
                    case 6:
                        ZShrPrefencs.getInstance().setPosMax(30);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBigNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setBigNumber(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setBigNumber(1);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBigDianFu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ZShrPrefencs.getInstance().setBigDianFu(0);
                        break;
                    case 1:
                        ZShrPrefencs.getInstance().setBigDianFu(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
