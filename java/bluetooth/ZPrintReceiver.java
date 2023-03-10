/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gprinter.command.EscCommand;
import com.gprinter.command.GpUtils;
import com.infinity.jerry.yyd_tms.data.AppUserToken;
import com.infinity.jerry.yyd_tms.data.BillingDrawMain;
import com.infinity.jerry.yyd_tms.ui.activity.MainActivity;
import com.infinity.jerry.yyd_tms.utils.DateUtil;
import com.infinity.jerry.yyd_tms.utils.StringUtils;
import com.infinity.jerry.yyd_tms.utils.ZDoubleFormat;
import com.infinity.jerry.yyd_tms.utils.ZShrPrefencs;

import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.DataForSendToPrinterPos80;
import net.posprinter.utils.DataForSendToPrinterTSC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by root on 2/16/17.
 */

public class ZPrintReceiver extends BroadcastReceiver {

    private Context context;
    private int requestState = 0;
    public static final int REQUEST_BILL = 64;
    public static final int REQUEST_LOAD = 128;

    public int copyNumber = 1;
    private boolean isSign = false;
    private BillingDrawMain printEntity;
    private List<BillingDrawMain> billEntity;

    private int buda = 0;

    public void setBuda(int buda) {
        this.buda = buda;
    }

    public ZPrintReceiver(Context context) {
        this.context = context;
    }

    public void xsetBillPrintEntity(BillingDrawMain entity, int requestState, int number, boolean isSign) {
        this.printEntity = entity;
        this.requestState = requestState;
        this.copyNumber = number;
        this.isSign = isSign;
    }

    public void setLoadPrintEntity(List<BillingDrawMain> billEntity, int requestState) {
        this.billEntity = billEntity;
        this.requestState = requestState;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // GpCom.ACTION_DEVICE_REAL_STATUS ????????????IntentFilter
        if (action.equals("")) {
            //????????????????????????????????????????????????????????????
            //??????????????????????????????????????????
            String str;

        }
    }

    private OnPrintStateChangeListener mListener;

    public void setOnPrintStateChangeListener(OnPrintStateChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnPrintStateChangeListener {

        void stateOn(String string);

        void stateOff(String string);

        void stateListener(String string);
    }


    //????????????
    public void sendBillRecepit(final int number) {
        short temp = 0;
        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            temp = 14;
        } else {
            temp = 12;
        }
        int loop = 1;
        boolean isCut = true;
        int xxxx = ZShrPrefencs.getInstance().getCopyCount();
        switch (xxxx) {
            case 0:
                loop = 1;
                isCut = true;
                break;
            case 1:
                loop = 1;
                isCut = true;
                break;
            case 2:
                loop = 2;
                isCut = true;
                break;
            case 3:
                loop = 3;
                isCut = false;
                break;
            case 4:
                loop = 3;
                isCut = true;
                break;
        }
        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            final EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            for (int i = 0; i < loop; i++) {
                String fuckOff = printEntity.getTotal_freight_receipts();
                String otherFee = "";
                String mutiFee = "";
                if (otherFee != null) {
                    try {
                        String[] shit = fuckOff.split("&&");
                        otherFee = shit[0];
                        mutiFee = shit[1];
                    } catch (Exception e) {
                        Log.e("TAG", "shit is happened");
                    }
                }

                if (ZShrPrefencs.getInstance().getBigNumber() == 1) {
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    String aa = StringUtils.getNullablString(printEntity.getArticle_number());
                    if (aa != null) {
                        esc.addText(aa.substring(4, aa.length()));
                        esc.addPrintAndLineFeed();
                    }
                }

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                if (!isSign) {
                    esc.addText(String.format("%s?????????(" + (i + 1) + ")???", AppUserToken.getInstance().getResult().getCompany_name()));
                } else {
                    esc.addText(String.format("%s?????????", AppUserToken.getInstance().getResult().getCompany_name()));
                }

                esc.addPrintAndLineFeed();
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(printEntity.getInvoice_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String aa = date.getHours() + "" + (String.valueOf(date.getMinutes()).length() == 1 ? "0" + String.valueOf(date.getMinutes()) : String.valueOf(date.getMinutes()));
                int bb = Integer.parseInt(aa);
                int cc = ZShrPrefencs.getInstance().getTimeDistr();
                String ban = "";
                if (cc != 0) {
                    if (bb <= cc) {
                        ban = "(??????)";
                    } else {
                        ban = "(??????)";
                    }
                }
                if (printEntity.getTransit_destination() != null) {
                    esc.addText(printEntity.getStartStation() + "???" + StringUtils.getNullablString(printEntity.getTransit_destination()) + ban + "\n");
                } else {
                    esc.addText(printEntity.getStartStation() + "???" + StringUtils.getNullablString(printEntity.getEndStation()) + ban + "\n");
                }
                esc.addPrintAndLineFeed();

                if (ZShrPrefencs.getInstance().getHintParams() != 0 && i == 0) {
                    esc.addText("*??????????????????*");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                if (printEntity.getInvoice_date() != null) {
                    if (buda == 9999) {
                        esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getInvoice_date())) + "(???)");
                    } else {
                        esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getInvoice_date())));
                    }
                } else {
                    esc.addText("????????????: ");
                }
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 14);
                esc.addText(String.format("??????: %s", StringUtils.getNullablString(printEntity.getUser_name())));
                esc.addPrintAndLineFeed();
//
//            esc.addText(String.format("?????????: %s", StringUtils.getNullablString(printEntity.getWaybill_number())));
//            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//            esc.addSetAbsolutePrintPosition((short) 14);
//
//            esc.addPrintAndLineFeed();

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            if (printEntity.getInput_article_number() != null) {
//                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()
//                        + "(" + printEntity.getInput_article_number() + ")"));
//            } else {
//                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()));
//            }

                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()) + " ????????????: " + StringUtils.getNullablString(printEntity.getInput_article_number()));

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();


                String getterName = printEntity.getConsignee();
                String getterPhone = printEntity.getConsignee_phone();
                int reeee = ZShrPrefencs.getInstance().getReciveSize();

                if (getterName == null || getterName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("?????????: %s", getterName));
                }
                if (reeee == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                }
                if (getterPhone == null || getterPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("??????: %s", getterPhone));
                }


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String senderName = printEntity.getConsigner();
                String senderPhone = printEntity.getConsigner_phone();

                int sendddd = ZShrPrefencs.getInstance().getSendSize();

                if (senderName == null || senderName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("?????????: %s", senderName));
                }

                if (sendddd == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                }
                if (senderPhone == null || senderPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("??????: %s", senderPhone));
                }

//                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//                esc.addText(String.format("?????????: %s", StringUtils.getNullablString(printEntity.getConsigner())));
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("??????: %s", StringUtils.getNullablString(printEntity.getConsigner_phone())));
                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                int numberrrr = ZShrPrefencs.getInstance().getNumberSize();
                short bbb = 12;
                if (numberrrr == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    bbb = 12;
                } else {
                    bb = temp;
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                }
                esc.addText("??????: " + printEntity.getQuantity() + "???");
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) bb);
                esc.addText("[" + printEntity.getDelivery_method() + "]");


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getGoods_name())));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) temp);
                esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getPackaging())));
                esc.addPrintAndLineFeed();

                esc.addText("------------------------------------------------");

                String shitON = "";
                if (ZShrPrefencs.getInstance().getPriceDetail() == 1) {
                    shitON = "????????????: ";
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addPrintAndLineFeed();
                    if (printEntity.getFreight() == null || printEntity.getFreight() == 0.0) {
                        esc.addText("??????: 0???");
                    } else {
                        esc.addText(String.format("??????: %s???", ZDoubleFormat.zFormat(printEntity.getFreight().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);

                    if (printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) {
                        esc.addText("?????????: " + "0???");
                    } else {
                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getAdvance().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    if (printEntity.getDelivery_fee() == null || printEntity.getDelivery_fee() == 0.0) {
                        esc.addText("?????????: " + "0???");
                    } else {
                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getDelivery_fee().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                    if (printEntity.getValuation_fee() == null || printEntity.getValuation_fee() == 0.0) {
                        esc.addText("?????????: " + "0???");
                    } else {
                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getValuation_fee().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    esc.addText("????????????: " + otherFee);
//                    esc.addPrintAndLineFeed();
                } else {
                    shitON = "??????: ";
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                } else {
                    esc.addPrintAndLineFeed();
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                } else {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                }


                if (printEntity.getCollection_fee() == null || printEntity.getCollection_fee().equals("")) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("??????: 0???");
                    } else {
                        esc.addText("??????: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0???");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("??????: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0???" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "???")));
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("??????: " + ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "???");
                    } else {
                        esc.addText("??????: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText(ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "???");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("??????: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0???" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "???")));
                        }
                    }
                }

                esc.addPrintAndLineFeed();

                if (printEntity.getTotal_freight() == null || printEntity.getTotal_freight() == 0.0) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText(shitON + "0???[" + mutiFee + "]");
                        } else {
                            esc.addText(shitON + "0???[" + printEntity.getPayment_method() + "]");
                        }
                    } else {
                        esc.addText(shitON);
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0???");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText(String.format(shitON + "%s???[" + mutiFee + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        } else {
                            esc.addText(String.format(shitON + "%s???[" + printEntity.getPayment_method() + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        }
                    } else {
                        esc.addText(String.format(shitON));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);

                        double advance = (printEntity.getAdvance() == null ? 0.0 : printEntity.getAdvance());

                        Double total_freight = printEntity.getTotal_freight();
                        if (total_freight == null) {
                            total_freight = 0.0;
                        }
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            total_freight = total_freight - advance;
                        }

                        esc.addText(String.format("%s???", ZDoubleFormat.zFormat(total_freight.toString())));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                }
//                if (isSign) {
//                    Double totalFee = StringUtils.getDoubleNotNull(printEntity.getFreight())
//                            + StringUtils.getDoubleNotNull(printEntity.getReceiving_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getHandling_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getForklift_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getPacking_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getUpstair_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getAdvance())
//                            + StringUtils.getDoubleNotNull(printEntity.getDelivery_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getValuation_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getCollection_fee())
//                            - StringUtils.getDoubleNotNull(printEntity.getCash_payment());
//                    esc.addText("????????????: " + ZDoubleFormat.zFormat(totalFee.toString()) + "???");
//                    esc.addPrintAndLineFeed();
//                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

                Double total = 0.0;
                Double daishou = 0.0;
                double allMoney = 0.0;
                if (printEntity.getTotal_freight() != null) {
                    total = printEntity.getTotal_freight();
                }
                if (printEntity.getCollection_fee() != null) {
                    daishou = printEntity.getCollection_fee();
                }

                if (printEntity.getPayment_method() != null) {
                    if (printEntity.getPayment_method().equals("??????")) {
                        allMoney = total + daishou;
                    } else if (printEntity.getPayment_method().equals("??????")) {
                        allMoney = daishou;
                    } else if (printEntity.getPayment_method().equals("?????????")) {
                        Double collect_payment = printEntity.getCollect_payment();
                        int tempx = 0;
                        if (collect_payment == null) {
                            tempx = 0;
                        } else {
                            tempx = Integer.parseInt(ZDoubleFormat.zFormat(String.valueOf(collect_payment)));
                        }
                        allMoney = tempx + daishou;
                    }
                }

                if (!isSign) {
                    esc.addText("     ??????: " + ZDoubleFormat.zFormat(allMoney + "") + "???");
                } else {
                    esc.addText("     ??????: " + ZDoubleFormat.zFormat(allMoney + "") + "???");
                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                if (printEntity.getBarCode() != null) {
                    if (ZShrPrefencs.getInstance().getIsBarCode() == 1) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                        esc.addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION.BELOW);
                        // ????????????????????????????????????????????????
                        // ?????????????????????60???
                        esc.addSetBarcodeHeight((byte) 80);
                        // ???????????????????????????1
                        esc.addSetBarcodeWidth((byte) 2);
                        // ??????Code128???
                        String barCode = printEntity.getBarCode();
                        if (i == 0) {
                            barCode = "99" + barCode;
                        }
                        esc.addCODE128(esc.genCode128(StringUtils.getNullablString(barCode)));
                        esc.addPrintAndLineFeed();
                    }
                }

//                esc.addText("------------------------------------------------");
                if (printEntity.getRemarks() != null && !printEntity.getRemarks().equals("")) {
                    esc.addText("??????: " + printEntity.getRemarks());
                    esc.addPrintAndLineFeed();
                }

                if (isSign) {
                    esc.addText("?????????: ");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                int space = ZShrPrefencs.getInstance().getSpaceCount();

                for (int p = 0; p < space; p++) {
                    esc.addPrintAndLineFeed();
                }

                String notice = printEntity.getLogistics_notice();
                if (!isSign) {
                    if (notice != null && !notice.isEmpty()) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText("??????");
                        esc.addPrintAndLineFeed();
                        esc.addText(notice);
                    }
                }
                if (AppUserToken.getInstance().getResult().getLogistics_uuid() == 2306) {
                    esc.addPrintAndLineFeed();
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    esc.addText("???????????????3??????");
                }
//            List<Declaration> notices = AppUserToken.getInstance().getDeclars();
//            if (notices != null && notices.size() != 0) {
//                for (int k = 0; k < notices.size(); k++) {
//                    esc.addText((k + 1) + "," + notices.get(k).getLogistics_notice());
//                    esc.addPrintAndLineFeed();
//                }
//            } else {
//                esc.addText("1,??????????????????????????????;?????????????????????????????????????????????");
//                esc.addPrintAndLineFeed();
//                esc.addText("2,????????????????????????????????????(??????????????????)??????????????????????????????????????????10??????????????????????????????6%??????????????????");
//                esc.addPrintAndLineFeed();
//                esc.addText("3,??????????????????????????????????????????????????????????????????");
//                esc.addPrintAndLineFeed();
//            }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addPrintAndLineFeed();
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String pointName = AppUserToken.getInstance().getResult().getPoint_name();
                String pointPhone = printEntity.getStartPoint_phone();

                String endName = printEntity.getEndStation();
                String endPoint = printEntity.getPoint_owner_phone_o();


                esc.addText("????????????: " + StringUtils.getNullablString(pointName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (pointPhone != null) {
                    String tempaaa = pointPhone.split(",")[0];
                    esc.addText("????????????: " + tempaaa);
                }
                esc.addPrintAndLineFeed();
                if (pointPhone != null) {
                    if (pointPhone.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();
                esc.addText("????????????: " + StringUtils.getNullablString(endName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (endPoint != null) {
                    String tempxx = endPoint.split(",")[0];
                    esc.addText("????????????: " + tempxx);
                    esc.addPrintAndLineFeed();
                }

                if (endPoint != null) {
                    if (endPoint.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                esc.addText(" ???????????????????????????" + "  www.chinniu.com");
                esc.addPrintAndLineFeed();
//                esc.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
                esc.addPrintAndFeedLines((byte) 3);
                esc.addPrintAndFeedLines((byte) 1);

                if (loop != 1 && i != loop - 1) {
                    if (xxxx != 3) {
                        esc.addCutPaper();
                    } else {
                        if (i != 1) {
                            esc.addCutPaper();
                        }
                    }
                }
            }
            //for ????????????
            esc.addCutPaper();
            esc.addQueryPrinterStatus();
            Vector<Byte> datas = esc.getCommand();//????????????
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
        } else {
            MainActivity.Companion.getBinder().writeDataByYouself(
                    new UiExecute() {
                        @Override
                        public void onsucess() {
                            MainActivity.Companion.getBinder().disconnectCurrentPort(new UiExecute() {
                                @Override
                                public void onsucess() {
                                    Log.e("TAG", "???????????????????????????");
                                }

                                @Override
                                public void onfailed() {
                                    Log.e("TAG", "???????????????????????????");
                                }
                            });

                        }

                        @Override
                        public void onfailed() {

                        }
                    }, new ProcessData() {
                        @Override
                        public List<byte[]> processDataBeforeSend() {
                            List<byte[]> list = new ArrayList<byte[]>();
                            list.add(DataForSendToPrinterPos80.initializePrinter());
                            int loop = 1;
                            boolean isCut = true;
                            int xxxx = ZShrPrefencs.getInstance().getCopyCount();
                            switch (xxxx) {
                                case 0:
                                    loop = 1;
                                    isCut = true;
                                    break;
                                case 1:
                                    loop = 1;
                                    isCut = true;
                                    break;
                                case 2:
                                    loop = 2;
                                    isCut = true;
                                    break;
                                case 3:
                                    loop = 3;
                                    isCut = false;
                                    break;
                                case 4:
                                    loop = 3;
                                    isCut = true;
                                    break;
                            }
                            for (int i = 0; i < loop; i++) {
                                final EscCommand esc = new EscCommand();
                                String fuckOff = printEntity.getTotal_freight_receipts();
                                String otherFee = "";
                                String mutiFee = "";
                                if (otherFee != null) {
                                    try {
                                        String[] shit = fuckOff.split("&&");
                                        otherFee = shit[0];
                                        mutiFee = shit[1];
                                    } catch (Exception e) {
                                        Log.e("TAG", "shit is happened");
                                    }
                                }

                                if (ZShrPrefencs.getInstance().getBigNumber() == 1) {
                                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    String aa = StringUtils.getNullablString(printEntity.getArticle_number());
                                    if (aa != null) {
                                        esc.addText(aa.substring(4, aa.length()));
                                        esc.addPrintAndLineFeed();
                                    }
                                }

                                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                if (!isSign) {
                                    esc.addText(String.format("%s?????????(" + (i + 1) + ")???", AppUserToken.getInstance().getResult().getCompany_name()));
                                } else {
                                    esc.addText(String.format("%s?????????", AppUserToken.getInstance().getResult().getCompany_name()));
                                }

                                esc.addPrintAndLineFeed();
                                Date date = null;
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(printEntity.getInvoice_date());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String aa = date.getHours() + "" + (String.valueOf(date.getMinutes()).length() == 1 ? "0" + String.valueOf(date.getMinutes()) : String.valueOf(date.getMinutes()));

                                int bb = Integer.parseInt(aa);
                                int cc = ZShrPrefencs.getInstance().getTimeDistr();
                                String ban = "";
                                if (cc != 0) {
                                    if (bb <= cc) {
                                        ban = "(??????)";
                                    } else {
                                        ban = "(??????)";
                                    }
                                }
                                if (printEntity.getTransit_destination() != null) {
                                    esc.addText(printEntity.getStartStation() + "???" + StringUtils.getNullablString(printEntity.getTransit_destination()) + ban + "\n");
                                } else {
                                    esc.addText(printEntity.getStartStation() + "???" + StringUtils.getNullablString(printEntity.getEndStation()) + ban + "\n");
                                }
                                esc.addPrintAndLineFeed();

                                if (ZShrPrefencs.getInstance().getHintParams() != 0 && i == 0) {
                                    esc.addText("*??????????????????*");
                                    esc.addPrintAndLineFeed();
                                    esc.addPrintAndLineFeed();
                                }

                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                if (printEntity.getInvoice_date() != null) {
                                    if (buda == 9999) {
                                        esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getInvoice_date())) + "(???)");
                                    } else {
                                        esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getInvoice_date())));
                                    }
                                } else {
                                    esc.addText("????????????: ");
                                }
                                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc.addSetAbsolutePrintPosition((short) 12);
                                esc.addText(String.format("??????: %s", StringUtils.getNullablString(printEntity.getUser_name())));
                                esc.addPrintAndLineFeed();
//
//            esc.addText(String.format("?????????: %s", StringUtils.getNullablString(printEntity.getWaybill_number())));
//            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//            esc.addSetAbsolutePrintPosition((short) 14);
//
//            esc.addPrintAndLineFeed();

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            if (printEntity.getInput_article_number() != null) {
//                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()
//                        + "(" + printEntity.getInput_article_number() + ")"));
//            } else {
//                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()));
//            }

                                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()) + "????????????: " + StringUtils.getNullablString(printEntity.getInput_article_number()));

                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();


                                String getterName = printEntity.getConsignee();
                                String getterPhone = printEntity.getConsignee_phone();

                                int reeee = ZShrPrefencs.getInstance().getReciveSize();
                                if (getterName == null || getterName.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("????????????????????????");
                                } else {
                                    if (reeee == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("?????????: %s", getterName));
                                }
                                if (reeee == 0) {
                                    esc.addPrintAndLineFeed();
                                } else {
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                }
                                if (getterPhone == null || getterPhone.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("????????????????????????");
                                } else {
                                    if (reeee == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("??????: %s", getterPhone));
                                }


                                esc.addPrintAndLineFeed();

                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();

                                String senderName = printEntity.getConsigner();
                                String senderPhone = printEntity.getConsigner_phone();

                                int sendddd = ZShrPrefencs.getInstance().getSendSize();
                                if (senderName == null || senderName.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("????????????????????????");
                                } else {
                                    if (sendddd == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("?????????: %s", senderName));
                                }

                                if (sendddd == 0) {
                                    esc.addPrintAndLineFeed();
                                } else {
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                }
                                if (senderPhone == null || senderPhone.equals("")) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addText("????????????????????????");
                                } else {
                                    if (sendddd == 0) {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    } else {
                                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    }
                                    esc.addText(String.format("??????: %s", senderPhone));
                                }

                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();

                                int numberrrr = ZShrPrefencs.getInstance().getNumberSize();

                                if (numberrrr == 0) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                } else {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                }
                                esc.addText("??????: " + printEntity.getQuantity() + "???");
                                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc.addSetAbsolutePrintPosition((short) 12);
                                esc.addText("[" + printEntity.getDelivery_method() + "]");


                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                                esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getGoods_name())));
                                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc.addSetAbsolutePrintPosition((short) 12);
                                esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getPackaging())));
                                esc.addPrintAndLineFeed();

                                esc.addText("------------------------------------------------");

                                String shitON = "";
                                if (ZShrPrefencs.getInstance().getPriceDetail() == 1) {
                                    shitON = "????????????: ";
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                    esc.addPrintAndLineFeed();
                                    if (printEntity.getFreight() == null || printEntity.getFreight() == 0.0) {
                                        esc.addText("??????: 0???");
                                    } else {
                                        esc.addText(String.format("??????: %s???", ZDoubleFormat.zFormat(printEntity.getFreight().toString())));
                                    }

                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);

                                    if (printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) {
                                        esc.addText("?????????: " + "0???");
                                    } else {
                                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getAdvance().toString())));
                                    }
                                    esc.addPrintAndLineFeed();

                                    if (printEntity.getDelivery_fee() == null || printEntity.getDelivery_fee() == 0.0) {
                                        esc.addText("?????????: " + "0???");
                                    } else {
                                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getDelivery_fee().toString())));
                                    }
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                    if (printEntity.getValuation_fee() == null || printEntity.getValuation_fee() == 0.0) {
                                        esc.addText("?????????: " + "0???");
                                    } else {
                                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getValuation_fee().toString())));
                                    }
                                    esc.addPrintAndLineFeed();

                                    esc.addText("????????????: " + otherFee);
//                                    esc.addPrintAndLineFeed();
                                } else {
                                    shitON = "??????: ";
                                }

                                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                    esc.addSetAbsolutePrintPosition((short) 12);
                                } else {
                                    esc.addPrintAndLineFeed();
                                }

                                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                } else {
                                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                }

                                if (printEntity.getCollection_fee() == null || printEntity.getCollection_fee().equals("")) {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        esc.addText("??????: 0???");
                                    } else {
                                        esc.addText("??????: ");
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 7);
                                        esc.addText("0???");
                                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                            esc.addSetAbsolutePrintPosition((short) 12);
                                            esc.addText("??????: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0???" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "???")));
                                        }
                                    }
                                } else {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        esc.addText("??????: " + ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "???");
                                    } else {
                                        esc.addText("??????: ");
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 7);
                                        esc.addText(ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "???");
                                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                            esc.addSetAbsolutePrintPosition((short) 12);
                                            esc.addText("??????: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0???" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "???")));
                                        }
                                    }
                                }

                                esc.addPrintAndLineFeed();

                                if (printEntity.getTotal_freight() == null || printEntity.getTotal_freight() == 0.0) {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                                            esc.addText(shitON + "0???[" + mutiFee + "]");
                                        } else {
                                            esc.addText(shitON + "0???[" + printEntity.getPayment_method() + "]");
                                        }
                                    } else {
                                        esc.addText(shitON);
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 6);
                                        esc.addText("0???");
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 12);
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                                            esc.addText("[" + mutiFee + "]");
                                        } else {
                                            esc.addText("[" + printEntity.getPayment_method() + "]");
                                        }
                                    }
                                } else {
                                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                                            esc.addText(String.format(shitON + "%s???[" + mutiFee + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                                        } else {
                                            esc.addText(String.format(shitON + "%s???[" + printEntity.getPayment_method() + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                                        }
                                    } else {
                                        esc.addText(String.format(shitON));
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 6);

                                        double advance = (printEntity.getAdvance() == null ? 0.0 : printEntity.getAdvance());

                                        Double total_freight = printEntity.getTotal_freight();
                                        if (total_freight == null) {
                                            total_freight = 0.0;
                                        }
                                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                                            total_freight = total_freight - advance;
                                        }
                                        esc.addText(String.format("%s???", ZDoubleFormat.zFormat(total_freight.toString())));
                                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                        esc.addSetAbsolutePrintPosition((short) 12);
                                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                                            esc.addText("[" + mutiFee + "]");
                                        } else {
                                            esc.addText("[" + printEntity.getPayment_method() + "]");
                                        }
                                    }
                                }

//                                if (isSign) {
//                                    Double totalFee = StringUtils.getDoubleNotNull(printEntity.getFreight())
//                                            + StringUtils.getDoubleNotNull(printEntity.getReceiving_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getHandling_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getForklift_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getPacking_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getUpstair_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getAdvance())
//                                            + StringUtils.getDoubleNotNull(printEntity.getDelivery_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getValuation_fee())
//                                            + StringUtils.getDoubleNotNull(printEntity.getCollection_fee())
//                                            - StringUtils.getDoubleNotNull(printEntity.getCash_payment());
//                                    esc.addText("????????????: " + ZDoubleFormat.zFormat(totalFee.toString()) + "???");
//                                    esc.addPrintAndLineFeed();
//                                }


                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();


                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

                                Double total = 0.0;
                                Double daishou = 0.0;
                                double allMoney = 0.0;
                                if (printEntity.getTotal_freight() != null) {
                                    total = printEntity.getTotal_freight();
                                }
                                if (printEntity.getCollection_fee() != null) {
                                    daishou = printEntity.getCollection_fee();
                                }
                                if (printEntity.getPayment_method() != null) {
                                    if (printEntity.getPayment_method().equals("??????")) {
                                        allMoney = total + daishou;
                                    } else if (printEntity.getPayment_method().equals("??????")) {
                                        allMoney = daishou;
                                    } else if (printEntity.getPayment_method().equals("?????????")) {
                                        Double collect_payment = printEntity.getCollect_payment();
                                        int tempx = 0;
                                        if (collect_payment == null) {
                                            tempx = 0;
                                        } else {
                                            tempx = Integer.parseInt(ZDoubleFormat.zFormat(String.valueOf(collect_payment)));
                                        }
                                        allMoney = tempx + daishou;
                                    }
                                }

                                if (!isSign) {
                                    esc.addText("     ??????: " + ZDoubleFormat.zFormat(allMoney + "") + "???");
                                } else {
                                    esc.addText("     ??????: " + ZDoubleFormat.zFormat(allMoney + "") + "???");
                                }

                                esc.addPrintAndLineFeed();
                                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc.addText("------------------------------------------------");
                                esc.addPrintAndLineFeed();

                                Vector<Byte> datas = esc.getCommand();//????????????
                                byte[] bytes = GpUtils.ByteTo_byte(datas);
                                list.add(bytes);

                                if (printEntity.getBarCode() != null) {
                                    if (ZShrPrefencs.getInstance().getIsBarCode() == 1) {
                                        list.add(DataForSendToPrinterPos80.selectHRICharacterPrintPosition(2));
//                                //??????????????????,??????????????????????????????????????????
                                        list.add(DataForSendToPrinterPos80.selectAlignment(1));
                                        list.add(DataForSendToPrinterPos80.setBarcodeWidth(2));
                                        list.add(DataForSendToPrinterPos80.setBarcodeHeight(80));
                                        if (!printEntity.getBarCode().isEmpty()) {
                                            if (i == 0) {
                                                list.add(DataForSendToPrinterPos80.printBarcode(70, 24, "99" + printEntity.getBarCode()));
                                                list.add(DataForSendToPrinterPos80.printAndFeedLine());
                                            } else {
                                                list.add(DataForSendToPrinterPos80.printBarcode(70, 22, printEntity.getBarCode()));
                                            }
                                        }
                                        list.add(DataForSendToPrinterPos80.printAndFeedLine());
                                    }
                                }


                                EscCommand esc1 = new EscCommand();
                                if (printEntity.getRemarks() != null && !printEntity.getRemarks().equals("")) {
                                    esc1.addText("??????: " + printEntity.getRemarks());
                                    esc1.addPrintAndLineFeed();
                                }

                                int space = ZShrPrefencs.getInstance().getSpaceCount();

                                for (int p = 0; p < space; p++) {
                                    esc1.addPrintAndLineFeed();
                                }

                                if (isSign) {
                                    esc1.addText("?????????: ");
                                    esc1.addPrintAndLineFeed();
                                    esc1.addPrintAndLineFeed();
                                }

                                String notice = printEntity.getLogistics_notice();
                                if (!isSign) {
                                    if (notice != null && !notice.isEmpty()) {
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                        esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                        esc1.addText("??????");
                                        esc1.addPrintAndLineFeed();
                                        esc1.addText(notice);
                                    }
                                }
                                if (AppUserToken.getInstance().getResult().getLogistics_uuid() == 2306) {
                                    esc1.addPrintAndLineFeed();
                                    esc1.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                    esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                                    esc1.addText("???????????????3??????");
                                }
//            List<Declaration> notices = AppUserToken.getInstance().getDeclars();
//            if (notices != null && notices.size() != 0) {
//                for (int k = 0; k < notices.size(); k++) {
//                    esc.addText((k + 1) + "," + notices.get(k).getLogistics_notice());
//                    esc.addPrintAndLineFeed();
//                }
//            } else {
//                esc.addText("1,??????????????????????????????;?????????????????????????????????????????????");
//                esc.addPrintAndLineFeed();
//                esc.addText("2,????????????????????????????????????(??????????????????)??????????????????????????????????????????10??????????????????????????????6%??????????????????");
//                esc.addPrintAndLineFeed();
//                esc.addText("3,??????????????????????????????????????????????????????????????????");
//                esc.addPrintAndLineFeed();
//            }
                                esc1.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc1.addPrintAndLineFeed();
                                esc1.addText("------------------------------------------------");
                                esc1.addPrintAndLineFeed();

                                String pointName = AppUserToken.getInstance().getResult().getPoint_name();
                                String pointPhone = printEntity.getStartPoint_phone();

                                String endName = printEntity.getEndStation();
                                String endPoint = printEntity.getPoint_owner_phone_o();


                                esc1.addText("????????????: " + StringUtils.getNullablString(pointName));
                                esc1.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc1.addSetAbsolutePrintPosition((short) 10);

                                if (pointPhone != null) {
                                    String tempaaa = pointPhone.split(",")[0];
                                    esc1.addText("????????????: " + tempaaa);
                                }
                                esc1.addPrintAndLineFeed();
                                if (pointPhone != null) {
                                    if (pointPhone.split(",").length == 2) {
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                                        esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                        esc1.addText(pointPhone.split(",")[1]);
                                        esc1.addPrintAndLineFeed();
                                    }
                                }
                                esc1.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                                esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                esc1.addText("------------------------------------------------");
                                esc1.addPrintAndLineFeed();
                                esc1.addText("????????????: " + StringUtils.getNullablString(endName));
                                esc1.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                                esc1.addSetAbsolutePrintPosition((short) 10);

                                if (endPoint != null) {
                                    String temp = endPoint.split(",")[0];
                                    esc1.addText("????????????: " + temp);
                                    esc1.addPrintAndLineFeed();
                                }

                                if (endPoint != null) {
                                    if (endPoint.split(",").length == 2) {
                                        esc1.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                                        esc1.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                                        esc1.addText(endPoint.split(",")[1]);
                                        esc1.addPrintAndLineFeed();
                                    }
                                }

                                esc1.addText("------------------------------------------------");
                                esc1.addPrintAndLineFeed();

                                esc1.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                                esc1.addText(" ???????????????????????????" + "  www.chinniu.com");
                                esc1.addPrintAndLineFeed();
//                                esc1.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
                                esc1.addPrintAndFeedLines((byte) 3);
                                esc1.addPrintAndFeedLines((byte) 1);

                                Vector<Byte> datas1 = esc1.getCommand();//????????????
                                byte[] bytes1 = GpUtils.ByteTo_byte(datas1);
                                list.add(bytes1);

                                if (loop != 1 && i != loop - 1) {
                                    if (xxxx != 3) {
                                        list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(1));
                                    } else {
                                        if (i != 1) {
                                            list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(1));
                                        }
                                    }
                                }
                            }
                            //for ????????????
                            EscCommand esc2 = new EscCommand();
                            esc2.addCutPaper();
                            esc2.addQueryPrinterStatus();
                            Vector<Byte> datas2 = esc2.getCommand();//????????????
                            byte[] bytes2 = GpUtils.ByteTo_byte(datas2);
                            list.add(bytes2);
                            return list;
                        }
                    });

        }

    }

    public void sendPosRecepit(final BillingDrawMain printEntity) {
        MainActivity.Companion.getBinderX().writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                Log.e("TAG", "xy????????????");
                MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        Log.e("TAG", "?????????????????????????????????");
                    }

                    @Override
                    public void onfailed() {
                        Log.e("TAG", "?????????????????????????????????");
                    }
                });
            }

            @Override
            public void onfailed() {
                Log.e("TAG", "xy????????????");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                long count = printEntity.getQuantity();

                int max = ZShrPrefencs.getInstance().getPosMax();

                if (max != 0) {
                    if (count >= max) {
                        count = max;
                    }
                }

                ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (int i = 0; i < count; i++) {
                    //????????????????????????????????????????????????????????????????????????gbk??????????????????????????????????????????????????????????????????????????????
                    DataForSendToPrinterTSC.setCharsetName("gbk");
                    //?????????????????????gbk
                    //????????????????????????????????????byte[]??????,???????????????
                    //???????????????size????????????,???60mm,???30mm,??????????????????dot???inch????????????????????????????????????????????????
                    byte[] data = DataForSendToPrinterTSC.sizeBymm(78, 42);
                    list.add(data);
                    //??????Gap,??????
                    list.add(DataForSendToPrinterTSC.gapBymm(0, 0));
                    //????????????
                    list.add(DataForSendToPrinterTSC.cls());
                    list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));
                    list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "??? " + (i + 1) + " ???"));
//                    String station = printEntity.getStartStation() + printEntity.getArticle_number()
//                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());
//                    String startStation = StringUtils.getNullablString(printEntity.getStartStation());
//                    String endStation = StringUtils.getNullablString(printEntity.getEndStation());

                    String station = "";

                    if (printEntity.getTransit_destination() != null) {
                        station = printEntity.getTransit_destination();
                    } else {
                        station = printEntity.getEndStation();
                    }

                    String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

                    int length = temp.length();
                    int x = 0;
                    switch (length) {
                        case 12:
                            x = 80;
                            break;
                        case 11:
                            x = 100;
                            break;
                        case 10:
                            x = 130;
                            break;
                        default:
                            x = 80;
                            break;
                    }

                    list.add(DataForSendToPrinterTSC.text(x, 70, "TSS24.BF2", 0, 2, 2, temp));

//                    list.add(DataForSendToPrinterTSC.text(20, 135, "TSS24.BF2", 0, 1, 1, StringUtils.getNullablString(printEntity.getConsignee()) + " ???"));
                    list.add(DataForSendToPrinterTSC.text(400, 135, "TSS24.BF2", 0, 1, 1, "??????: " + StringUtils.getNullablString(printEntity.getGoods_name())));

//                    list.add(DataForSendToPrinterTSC.barCode(
//                            135, 165, "128", 90, 1, 0, 2, 2,
//                            printEntity.getBarCode()));

                    list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " ???"));


                    list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com ??????"));
                    list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

                    //??????
                    list.add(DataForSendToPrinterTSC.print(1));
                }
                return list;
            }
        });
    }

    public void sendPosRecepitSimple(final BillingDrawMain printEntity) {
        MainActivity.Companion.getBinderX().writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                Log.e("TAG", "xy????????????");
                MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        Log.e("TAG", "?????????????????????????????????");
                    }

                    @Override
                    public void onfailed() {
                        Log.e("TAG", "?????????????????????????????????");
                    }
                });
            }

            @Override
            public void onfailed() {
                Log.e("TAG", "xy????????????");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                long count = printEntity.getQuantity();
                int max = ZShrPrefencs.getInstance().getPosMax();
                if (max != 0) {
                    if (count >= max) {
                        count = max;
                    }
                }
                ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (int i = 0; i < count; i++) {
                    //????????????????????????????????????????????????????????????????????????gbk??????????????????????????????????????????????????????????????????????????????
                    DataForSendToPrinterTSC.setCharsetName("gbk");
                    //?????????????????????gbk
                    //????????????????????????????????????byte[]??????,???????????????
                    //???????????????size????????????,???60mm,???30mm,??????????????????dot???inch????????????????????????????????????????????????
                    byte[] data = DataForSendToPrinterTSC.sizeBymm(78, 42);
                    list.add(data);
                    //??????Gap,??????
                    list.add(DataForSendToPrinterTSC.gapBymm(0, 0));
                    //?????? ??????
                    list.add(DataForSendToPrinterTSC.cls());
                    list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));

                    String station = "";

                    if (printEntity.getTransit_destination() != null) {
                        station = printEntity.getTransit_destination();
                    } else {
                        station = printEntity.getEndStation();
                    }

                    String temp = printEntity.getArticle_number()
                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());

                    list.add(DataForSendToPrinterTSC.text(220, 30, "TSS24.BF2", 0, 3, 3, station));
                    list.add(DataForSendToPrinterTSC.text(470, 30, "TSS24.BF2", 0, 1, 1, "??? " + (i + 1) + " ???"));
                    list.add(DataForSendToPrinterTSC.text(180, 140, "TSS24.BF2", 0, 3, 3, temp));
                    list.add(DataForSendToPrinterTSC.text(400, 180, "TSS24.BF2", 0, 2, 2, StringUtils.getNullablString(printEntity.getConsignee())));
                    list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com ??????"));
                    list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

                    //??????
                    list.add(DataForSendToPrinterTSC.print(1));
                }
                return list;
            }
        });
    }

    public void sendPosRecepitBigCompany(final BillingDrawMain printEntity) {
        MainActivity.Companion.getBinderX().writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                Log.e("TAG", "xy????????????");
                MainActivity.Companion.getBinderX().disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        Log.e("TAG", "?????????????????????????????????");
                    }

                    @Override
                    public void onfailed() {
                        Log.e("TAG", "?????????????????????????????????");
                    }
                });
            }

            @Override
            public void onfailed() {
                Log.e("TAG", "xy????????????");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                long count = printEntity.getQuantity();

                int max = ZShrPrefencs.getInstance().getPosMax();

                if (max != 0) {
                    if (count >= max) {
                        count = max;
                    }
                }

                ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (int i = 0; i < count; i++) {
                    //????????????????????????????????????????????????????????????????????????gbk??????????????????????????????????????????????????????????????????????????????
                    DataForSendToPrinterTSC.setCharsetName("gbk");
                    //?????????????????????gbk
                    //????????????????????????????????????byte[]??????,???????????????
                    //???????????????size????????????,???60mm,???30mm,??????????????????dot???inch????????????????????????????????????????????????
                    byte[] data = DataForSendToPrinterTSC.sizeBymm(78, 42);
                    list.add(data);
                    //??????Gap,??????
                    list.add(DataForSendToPrinterTSC.gapBymm(0, 0));
                    //????????????
                    list.add(DataForSendToPrinterTSC.cls());
                    list.add(DataForSendToPrinterTSC.text(20, 35, "TSS24.BF2", 0, 2, 2, AppUserToken.getInstance().getResult().getCompany_name()));
                    list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "??? " + (i + 1) + " ???"));

                    String station = "";

                    if (printEntity.getTransit_destination() != null) {
                        station = printEntity.getTransit_destination();
                    } else {
                        station = printEntity.getEndStation();
                    }

                    String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

                    int length = temp.length();
                    int x = 0;
                    switch (length) {
                        case 12:
                            x = 80;
                            break;
                        case 11:
                            x = 100;
                            break;
                        case 10:
                            x = 130;
                            break;
                        default:
                            x = 80;
                            break;
                    }

                    list.add(DataForSendToPrinterTSC.text(x, 90, "TSS24.BF2", 0, 2, 2, temp));

                    list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " ???"));

                    list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com ??????"));
                    list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

                    //??????
                    list.add(DataForSendToPrinterTSC.print(1));
                }
                return list;
            }
        });
    }

    public void sendPosBB(final BillingDrawMain printEntity) {
        boolean connState = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].getConnState();
        if (connState) {
            Log.e("TAG", "zxc1111");
        } else {
            Log.e("TAG", "zxc2222");
        }
        long count = printEntity.getQuantity();
        int max = ZShrPrefencs.getInstance().getPosMax();
        if (max != 0) {
            if (count >= max) {
                count = max;
            }
        }

        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < count; i++) {
            //????????????????????????????????????????????????????????????????????????gbk??????????????????????????????????????????????????????????????????????????????
            DataForSendToPrinterTSC.setCharsetName("gbk");
            //?????????????????????gbk
            //????????????????????????????????????byte[]??????,???????????????
            //???????????????size????????????,???60mm,???30mm,??????????????????dot???inch????????????????????????????????????????????????
            byte[] data = DataForSendToPrinterTSC.sizeBymm(80, 40);
            list.add(data);
            //??????Gap,??????
            list.add(DataForSendToPrinterTSC.gapBymm(2, 0));
            //?????? ??????
            list.add(DataForSendToPrinterTSC.cls());
            list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));
            list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "??? " + (i + 1) + " ???"));
//                    String station = printEntity.getStartStation() + printEntity.getArticle_number()
//                            .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());
//                    String startStation = StringUtils.getNullablString(printEntity.getStartStation());
//                    String endStation = StringUtils.getNullablString(printEntity.getEndStation());

            String station = "";

            if (printEntity.getTransit_destination() != null) {
                station = printEntity.getTransit_destination();
            } else {
                station = printEntity.getEndStation();
            }

            String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                    .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

            int length = temp.length();
            int x = 0;
            switch (length) {
                case 12:
                    x = 80;
                    break;
                case 11:
                    x = 100;
                    break;
                case 10:
                    x = 130;
                    break;
                default:
                    x = 80;
                    break;
            }

            list.add(DataForSendToPrinterTSC.text(x, 70, "TSS24.BF2", 0, 2, 2, temp));

//                    list.add(DataForSendToPrinterTSC.text(20, 135, "TSS24.BF2", 0, 1, 1, StringUtils.getNullablString(printEntity.getConsignee()) + " ???"));
            list.add(DataForSendToPrinterTSC.text(400, 135, "TSS24.BF2", 0, 1, 1, "??????: " + StringUtils.getNullablString(printEntity.getGoods_name())));

//                    list.add(DataForSendToPrinterTSC.barCode(
//                            135, 165, "128", 90, 1, 0, 2, 2,
//                            printEntity.getBarCode()));

            list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " ???"));


            list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com ??????"));
            list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

            //??????
            list.add(DataForSendToPrinterTSC.print(1));
        }

        Vector<Byte> datas = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.get(i).length; k++) {
                datas.add(list.get(i)[k]);
            }
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].sendDataImmediately(datas);
    }

    public void sendPosBBSimple(final BillingDrawMain printEntity) {
        boolean connState = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].getConnState();
        if (connState) {
            Log.e("TAG", "zxc1111");
        } else {
            Log.e("TAG", "zxc2222");
        }
        long count = printEntity.getQuantity();
        int max = ZShrPrefencs.getInstance().getPosMax();
        if (max != 0) {
            if (count >= max) {
                count = max;
            }
        }

        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < count; i++) {
            //????????????????????????????????????????????????????????????????????????gbk??????????????????????????????????????????????????????????????????????????????
            DataForSendToPrinterTSC.setCharsetName("gbk");
            //?????????????????????gbk
            //????????????????????????????????????byte[]??????,???????????????
            //???????????????size????????????,???60mm,???30mm,??????????????????dot???inch????????????????????????????????????????????????
            byte[] data = DataForSendToPrinterTSC.sizeBymm(80, 40);
            list.add(data);
            //??????Gap,??????
            list.add(DataForSendToPrinterTSC.gapBymm(2, 0));
            //?????? ??????
            list.add(DataForSendToPrinterTSC.cls());
            list.add(DataForSendToPrinterTSC.text(20, 30, "TSS24.BF2", 0, 1, 1, AppUserToken.getInstance().getResult().getCompany_name()));

            String station = "";

            if (printEntity.getTransit_destination() != null) {
                station = printEntity.getTransit_destination();
            } else {
                station = printEntity.getEndStation();
            }

            String temp = printEntity.getArticle_number()
                    .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length());

            list.add(DataForSendToPrinterTSC.text(220, 30, "TSS24.BF2", 0, 3, 3, station));
            list.add(DataForSendToPrinterTSC.text(470, 30, "TSS24.BF2", 0, 1, 1, "??? " + (i + 1) + " ???"));
            list.add(DataForSendToPrinterTSC.text(180, 140, "TSS24.BF2", 0, 3, 3, temp));
            list.add(DataForSendToPrinterTSC.text(400, 180, "TSS24.BF2", 0, 2, 2, StringUtils.getNullablString(printEntity.getConsignee())));
            list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com ??????"));
            list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

            //??????
            list.add(DataForSendToPrinterTSC.print(1));
        }

        Vector<Byte> datas = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.get(i).length; k++) {
                datas.add(list.get(i)[k]);
            }
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].sendDataImmediately(datas);
    }

    public void sendPostBBBigCompany(final BillingDrawMain printEntity) {
        boolean connState = DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].getConnState();
        if (connState) {
            Log.e("TAG", "zxc1111");
        } else {
            Log.e("TAG", "zxc2222");
        }
        long count = printEntity.getQuantity();
        int max = ZShrPrefencs.getInstance().getPosMax();
        if (max != 0) {
            if (count >= max) {
                count = max;
            }
        }

        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < count; i++) {
            //????????????????????????????????????????????????????????????????????????gbk??????????????????????????????????????????????????????????????????????????????
            DataForSendToPrinterTSC.setCharsetName("gbk");
            //?????????????????????gbk
            //????????????????????????????????????byte[]??????,???????????????
            //???????????????size????????????,???60mm,???30mm,??????????????????dot???inch????????????????????????????????????????????????
            byte[] data = DataForSendToPrinterTSC.sizeBymm(80, 40);
            list.add(data);
            //??????Gap,??????
            list.add(DataForSendToPrinterTSC.gapBymm(2, 0));
            //?????? ??????
            list.add(DataForSendToPrinterTSC.cls());
            list.add(DataForSendToPrinterTSC.text(20, 35, "TSS24.BF2", 0, 2, 2, AppUserToken.getInstance().getResult().getCompany_name()));
            list.add(DataForSendToPrinterTSC.text(430, 30, "TSS24.BF2", 0, 1, 1, "??? " + (i + 1) + " ???"));

            String station = "";

            if (printEntity.getTransit_destination() != null) {
                station = printEntity.getTransit_destination();
            } else {
                station = printEntity.getEndStation();
            }

            String temp = printEntity.getStartStation() + printEntity.getArticle_number()
                    .substring(printEntity.getArticle_number().length() - 6, printEntity.getArticle_number().length()) + station;

            int length = temp.length();
            int x = 0;
            switch (length) {
                case 12:
                    x = 80;
                    break;
                case 11:
                    x = 100;
                    break;
                case 10:
                    x = 130;
                    break;
                default:
                    x = 80;
                    break;
            }

            list.add(DataForSendToPrinterTSC.text(x, 90, "TSS24.BF2", 0, 2, 2, temp));

            list.add(DataForSendToPrinterTSC.text(80, 175, "TSS24.BF2", 0, 3, 3, StringUtils.getNullablString(printEntity.getConsignee()) + " ???"));


            list.add(DataForSendToPrinterTSC.text(20, 285, "TSS24.BF2", 0, 1, 1, "www.chinniu.com ??????"));
            list.add(DataForSendToPrinterTSC.text(400, 285, "TSS24.BF2", 0, 1, 1, printEntity.getInvoice_date()));

            //??????
            list.add(DataForSendToPrinterTSC.print(1));
        }

        Vector<Byte> datas = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < list.get(i).length; k++) {
                datas.add(list.get(i)[k]);
            }
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[1].sendDataImmediately(datas);
    }


    //????????????
    public void sendLoadRecepitX() {
        int aaaa = billEntity.size() % 10;
        int pages = billEntity.size() / 10;
        if (aaaa != 0) {
            pages = pages + 1;
        }
        EscCommand esc = new EscCommand();
        esc.addInitializePrinter();
        for (int i = 1; i <= pages; i++) {
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addPrintAndLineFeed();
            esc.addText(AppUserToken.getInstance().getResult().getPoint_name() + "????????????\n");
            esc.addPrintAndLineFeed();
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            esc.addText("???????????? " + DateUtil.getCUrrentDateYMDHM() + "   ?????????: " + billEntity.get(0).getPlate_number());
            esc.addPrintAndLineFeed();
            esc.addText("------------------------------------------------");

            if (i != pages) {
                for (int k = (i - 1) * 10; k < ((i - 1) * 10 + 10); k++) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);

                    esc.addText("??????: " + billEntity.get(k).getArticle_number());
                    esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 6);

                    if ((billEntity.get(k).getConsignee() == null
                            || billEntity.get(k).getConsignee().equals(""))
                            && (billEntity.get(k).getConsignee_phone() == null
                            || billEntity.get(k).getConsignee_phone().equals(""))) {

                        esc.addText("????????????????????????");
                        esc.addPrintAndLineFeed();

                    } else {
                        esc.addText((billEntity.get(k).getConsignee() == null ? "" : billEntity.get(k).getConsignee()) + " " + (billEntity.get(k).getConsignee_phone() == null ? "" : billEntity.get(k).getConsignee_phone()));
                        esc.addPrintAndLineFeed();

                    }
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText((billEntity.get(k).getStartStation()
                            + "----" + billEntity.get(k).getEndStation()) + "   " + "????????????: " + billEntity.get(k).getInvoice_date());
                    esc.addPrintAndLineFeed();

                    String builder = "??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollect_payment()) +
                            "   ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCash_payment()) +
                            "   ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getBack_payment()) +
                            "   ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getMonthly_payment());

                    esc.addText(builder);
                    esc.addPrintAndLineFeed();
                    esc.addText("??????: " + (billEntity.get(k).getGoods_name() == null ? "???" : billEntity.get(k).getGoods_name())
                            + "(" + (billEntity.get(k).getPackaging() == null ? "???" : billEntity.get(k).getPackaging()) + ", "
                            + billEntity.get(k).getDelivery_method() + ")" + " ??????: "
                            + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollection_fee())
                            + " ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getReturn_fee()));

                    esc.addPrintAndLineFeed();
                    esc.addText("------------------------------------------------");

                }

            } else {
                for (int k = (i - 1) * 10; k < billEntity.size(); k++) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addText("??????: " + billEntity.get(k).getArticle_number());

                    esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 6);
                    if ((billEntity.get(k).getConsignee() == null
                            || billEntity.get(k).getConsignee().equals(""))
                            && (billEntity.get(k).getConsignee_phone() == null
                            || billEntity.get(k).getConsignee_phone().equals(""))) {

                        esc.addText("????????????????????????");
                        esc.addPrintAndLineFeed();

                    } else {
                        esc.addText((billEntity.get(k).getConsignee() == null ? "" : billEntity.get(k).getConsignee()) + " " + (billEntity.get(k).getConsignee_phone() == null ? "" : billEntity.get(k).getConsignee_phone()));
                        esc.addPrintAndLineFeed();
                    }
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText((billEntity.get(k).getStartStation()
                            + "----" + billEntity.get(k).getEndStation()) + "   " + "????????????: " + billEntity.get(k).getInvoice_date());
                    esc.addPrintAndLineFeed();


                    String builder = "??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollect_payment()) +
                            "   ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCash_payment()) +
                            "   ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getBack_payment()) +
                            "   ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getMonthly_payment());

                    esc.addText(builder);
                    esc.addPrintAndLineFeed();
                    esc.addText("??????: " + (billEntity.get(k).getGoods_name() == null ? "???" : billEntity.get(k).getGoods_name())
                            + "(" + (billEntity.get(k).getPackaging() == null ? "???" : billEntity.get(k).getPackaging()) + ", "
                            + billEntity.get(k).getDelivery_method() + ")" + " ??????: "
                            + StringUtils.getZeroableStringForDouble(billEntity.get(k).getCollection_fee())
                            + " ??????: " + StringUtils.getZeroableStringForDouble(billEntity.get(k).getReturn_fee()));

                    esc.addPrintAndLineFeed();
                    esc.addText("------------------------------------------------");

                    if (k == billEntity.size() - 1) {
                        esc.addPrintAndLineFeed();
                        //?????? ?????? ?????? ????????????
                        double quantity = 0;
                        double freight = 0;
                        double advance = 0;
                        double valuation = 0;
                        double delivery = 0;
                        double receving = 0;
                        double upstair = 0;
                        double packing = 0;
                        double forklift = 0;
                        double handling = 0;

                        double collec_fee = 0;
                        double totalall = 0;
                        double cashPayment = 0;
                        double collectPayment = 0;
                        double back_payment = 0;
                        double monthly = 0;
                        double deduction = 0;
                        double re_fee = 0;

                        for (BillingDrawMain entity : billEntity) {
                            quantity += getNotNullDouble(Double.valueOf(entity.getQuantity()));
                            freight += getNotNullDouble(entity.getFreight());
                            advance += getNotNullDouble(entity.getAdvance());
                            valuation += getNotNullDouble(entity.getValuation_fee());
                            delivery += getNotNullDouble(entity.getDelivery_fee());
                            receving += getNotNullDouble(entity.getReceiving_fee());
                            upstair += getNotNullDouble(entity.getUpstair_fee());
                            packing += getNotNullDouble(entity.getPacking_fee());
                            forklift += getNotNullDouble(entity.getForklift_fee());
                            handling += getNotNullDouble(entity.getHandling_fee());
                            cashPayment += getNotNullDouble(entity.getCash_payment());
                            collectPayment += getNotNullDouble(entity.getCollect_payment());
                            collec_fee += getNotNullDouble(entity.getCollection_fee());
                            totalall += getNotNullDouble(entity.getTotal_freight());
                            back_payment += getNotNullDouble(entity.getBack_payment());
                            monthly += getNotNullDouble(entity.getMonthly_payment());
                            deduction += getNotNullDouble(entity.getPayment_deduction());
                            re_fee += getNotNullDouble(entity.getReturn_fee());
                        }

                        esc.addText("??????");
                        esc.addPrintAndLineFeed();
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(quantity));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(freight));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(advance));
                        esc.addPrintAndLineFeed();

                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(valuation));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(delivery));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(receving));
                        esc.addPrintAndLineFeed();

                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(upstair));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(packing));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(forklift));
                        esc.addPrintAndLineFeed();

                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(handling));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("????????????: " + StringUtils.getZeroableStringForDouble(totalall));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(collec_fee));
                        esc.addPrintAndLineFeed();

                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(cashPayment));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(collectPayment));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(back_payment));
                        esc.addPrintAndLineFeed();

                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(monthly));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 7);
                        esc.addText("?????????: " + StringUtils.getZeroableStringForDouble(deduction));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 14);
                        esc.addText("??????: " + StringUtils.getZeroableStringForDouble(re_fee));
                        esc.addPrintAndLineFeed();

                        esc.addText("------------------------------------------------");

                        esc.addText("????????????(??????): ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        esc.addText("?????????(??????): ");
                        esc.addPrintAndLineFeed();

                        esc.addText("????????????: " + AppUserToken.getInstance().getResult().getCompany_name());

                        esc.addPrintAndLineFeed();

                        esc.addText("??????: " + (AppUserToken.getInstance().getResult().getUser_name() == null ? "" : AppUserToken.getInstance().getResult().getUser_name()));
//                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//                        esc.addSetAbsolutePrintPosition((short) 7);
//                        esc.addText("???????????? " + DateUtil.getCUrrentDateYMDHM());
                        esc.addPrintAndLineFeed();

                        esc.addText("------------------------------------------------");
                        esc.addPrintAndLineFeed();
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                        esc.addText("???????????????????????????   www.chinniu.com");
                        esc.addPrintAndLineFeed();

                        esc.addPrintAndLineFeed();
                        esc.addPrintAndLineFeed();
//                        esc.addCutAndFeedPaper((byte) 3);
                    }
                }
            }
            //?????? ??? ??? ??????
            esc.addPrintAndLineFeed();
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addText("----???" + i + "???  ???" + (pages) + "???----");
            esc.addPrintAndLineFeed();
            esc.addCutAndFeedPaper((byte) 3);
        }

        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            Vector<Byte> datas = esc.getCommand();//????????????
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
        } else {
            final List<byte[]> list = new ArrayList<>();
            Vector<Byte> datas = esc.getCommand();//????????????
            byte[] bytes = GpUtils.ByteTo_byte(datas);
            list.add(bytes);

            MainActivity.Companion.getBinder().writeDataByYouself(
                    new UiExecute() {
                        @Override
                        public void onsucess() {
                            MainActivity.Companion.getBinder().disconnectCurrentPort(new UiExecute() {
                                @Override
                                public void onsucess() {
                                    Log.e("TAG", "???????????????????????????");
                                }

                                @Override
                                public void onfailed() {
                                    Log.e("TAG", "???????????????????????????");
                                }
                            });

                        }

                        @Override
                        public void onfailed() {

                        }
                    }, new ProcessData() {
                        @Override
                        public List<byte[]> processDataBeforeSend() {
                            list.add(DataForSendToPrinterPos80.initializePrinter());
                            return list;
                        }
                    });
        }
    }

    private Double getNotNullDouble(Double sth) {
        return sth != null ? sth : 0.0;
    }


    public void batchPrint(List<BillingDrawMain> entities) {
        short temp = 12;
        if (ZShrPrefencs.getInstance().getNetOn() == 0) {
            final EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            for (int i = 0; i < entities.size(); i++) {
                BillingDrawMain printEntity = entities.get(i);
                String fuckOff = printEntity.getTotal_freight_receipts();
                String otherFee = "";
                String mutiFee = "";
                if (otherFee != null) {
                    try {
                        String[] shit = fuckOff.split("&&");
                        otherFee = shit[0];
                        mutiFee = shit[1];
                    } catch (Exception e) {
                        Log.e("TAG", "shit is happened");
                    }
                }

                if (ZShrPrefencs.getInstance().getBigNumber() == 1) {
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    String aa = StringUtils.getNullablString(printEntity.getArticle_number());
                    if (aa != null) {
                        esc.addText(aa.substring(4, aa.length()));
                        esc.addPrintAndLineFeed();
                    }
                }

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                if (!isSign) {
                    esc.addText(String.format("%s?????????", AppUserToken.getInstance().getResult().getCompany_name()));
                } else {
                    esc.addText(String.format("%s?????????", AppUserToken.getInstance().getResult().getCompany_name()));
                }

                esc.addPrintAndLineFeed();
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(printEntity.getInvoice_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String aa = date.getHours() + "" + (String.valueOf(date.getMinutes()).length() == 1 ? "0" + String.valueOf(date.getMinutes()) : String.valueOf(date.getMinutes()));

                int bb = Integer.parseInt(aa);
                int cc = ZShrPrefencs.getInstance().getTimeDistr();
                String ban = "";
                if (cc != 0) {
                    if (bb <= cc) {
                        ban = "(??????)";
                    } else {
                        ban = "(??????)";
                    }
                }
                if (printEntity.getTransit_destination() != null) {
                    esc.addText(printEntity.getStartStation() + "???" + StringUtils.getNullablString(printEntity.getTransit_destination()) + ban + "\n");
                } else {
                    esc.addText(printEntity.getStartStation() + "???" + StringUtils.getNullablString(printEntity.getEndStation()) + ban + "\n");
                }
                esc.addPrintAndLineFeed();

                if (ZShrPrefencs.getInstance().getHintParams() != 0 && i == 0) {
                    esc.addText("*??????????????????*");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                if (printEntity.getInvoice_date() != null) {
                    if (buda == 9999) {
                        esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getInvoice_date())) + "(???)");
                    } else {
                        esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getInvoice_date())));
                    }
                } else {
                    esc.addText("????????????: ");
                }
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 14);
                esc.addText(String.format("??????: %s", StringUtils.getNullablString(printEntity.getUser_name())));
                esc.addPrintAndLineFeed();
//
//            esc.addText(String.format("?????????: %s", StringUtils.getNullablString(printEntity.getWaybill_number())));
//            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//            esc.addSetAbsolutePrintPosition((short) 14);
//
//            esc.addPrintAndLineFeed();

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            if (printEntity.getInput_article_number() != null) {
//                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()
//                        + "(" + printEntity.getInput_article_number() + ")"));
//            } else {
//                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()));
//            }

                esc.addText("??????: " + StringUtils.getNullablString(printEntity.getArticle_number()) + " ????????????: " + StringUtils.getNullablString(printEntity.getInput_article_number()));

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();


                String getterName = printEntity.getConsignee();
                String getterPhone = printEntity.getConsignee_phone();

                int reeee = ZShrPrefencs.getInstance().getReciveSize();
                if (getterName == null || getterName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("?????????: %s", getterName));
                }
                if (reeee == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 12);
                }
                if (getterPhone == null || getterPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (reeee == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("??????: %s", getterPhone));
                }


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String senderName = printEntity.getConsigner();
                String senderPhone = printEntity.getConsigner_phone();

                int sendddd = ZShrPrefencs.getInstance().getSendSize();
                if (senderName == null || senderName.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("?????????: %s", senderName));
                }

                if (sendddd == 0) {
                    esc.addPrintAndLineFeed();
                } else {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) 12);
                }
                if (senderPhone == null || senderPhone.equals("")) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addText("????????????????????????");
                } else {
                    if (sendddd == 0) {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    } else {
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    }
                    esc.addText(String.format("??????: %s", senderPhone));
                }


//                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
//                esc.addText(String.format("?????????: %s", StringUtils.getNullablString(printEntity.getConsigner())));
//                esc.addPrintAndLineFeed();
//                esc.addText(String.format("??????: %s", StringUtils.getNullablString(printEntity.getConsigner_phone())));
                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                int numberrrr = ZShrPrefencs.getInstance().getNumberSize();

                if (numberrrr == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                } else {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                }
                esc.addText("??????: " + printEntity.getQuantity() + "???");
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 12);
                esc.addText("[" + printEntity.getDelivery_method() + "]");


                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

                esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getGoods_name())));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) temp);
                esc.addText(String.format("????????????: %s", StringUtils.getNullablString(printEntity.getPackaging())));
                esc.addPrintAndLineFeed();

                esc.addText("------------------------------------------------");

                String shitON = "";
                if (ZShrPrefencs.getInstance().getPriceDetail() == 1) {
                    shitON = "????????????: ";
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                    esc.addPrintAndLineFeed();
                    if (printEntity.getFreight() == null || printEntity.getFreight() == 0.0) {
                        esc.addText("??????: 0???");
                    } else {
                        esc.addText(String.format("??????: %s???", ZDoubleFormat.zFormat(printEntity.getFreight().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);

                    if (printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) {
                        esc.addText("?????????: " + "0???");
                    } else {
                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getAdvance().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    if (printEntity.getDelivery_fee() == null || printEntity.getDelivery_fee() == 0.0) {
                        esc.addText("?????????: " + "0???");
                    } else {
                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getDelivery_fee().toString())));
                    }
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                    if (printEntity.getValuation_fee() == null || printEntity.getValuation_fee() == 0.0) {
                        esc.addText("?????????: " + "0???");
                    } else {
                        esc.addText(String.format("?????????: %s???", ZDoubleFormat.zFormat(printEntity.getValuation_fee().toString())));
                    }
                    esc.addPrintAndLineFeed();

                    esc.addText("????????????: " + otherFee);
//                    esc.addPrintAndLineFeed();
                } else {
                    shitON = "??????: ";
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                } else {
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                }

                if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                    esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                    esc.addSetAbsolutePrintPosition((short) temp);
                } else {
                    esc.addPrintAndLineFeed();
                }

                if (printEntity.getCollection_fee() == null || printEntity.getCollection_fee().equals("")) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("??????: 0???");
                    } else {
                        esc.addText("??????: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0???");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("??????: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0???" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "???")));
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        esc.addText("??????: " + ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "???");
                    } else {
                        esc.addText("??????: ");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText(ZDoubleFormat.zFormat(printEntity.getCollection_fee().toString()) + "???");
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                            esc.addSetAbsolutePrintPosition((short) 12);
                            esc.addText("??????: " + ((printEntity.getAdvance() == null || printEntity.getAdvance() == 0.0) ? "0???" : (ZDoubleFormat.zFormat(String.valueOf(printEntity.getAdvance())) + "???")));
                        }
                    }
                }
                esc.addPrintAndLineFeed();

                if (printEntity.getTotal_freight() == null || printEntity.getTotal_freight() == 0.0) {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText(shitON + "0???[" + mutiFee + "]");
                        } else {
                            esc.addText(shitON + "0???[" + printEntity.getPayment_method() + "]");
                        }
                    } else {
                        esc.addText(shitON);
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        esc.addText("0???");
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                } else {
                    if (ZShrPrefencs.getInstance().getPriceSize() == 0) {
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText(String.format(shitON + "%s???[" + mutiFee + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        } else {
                            esc.addText(String.format(shitON + "%s???[" + printEntity.getPayment_method() + "]", ZDoubleFormat.zFormat(printEntity.getTotal_freight().toString())));
                        }
                    } else {
                        esc.addText(String.format(shitON));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 6);
                        double advance = (printEntity.getAdvance() == null ? 0.0 : printEntity.getAdvance());

                        Double total_freight = printEntity.getTotal_freight();
                        if (total_freight == null) {
                            total_freight = 0.0;
                        }
                        if (ZShrPrefencs.getInstance().getBigDianFu() == 1) {
                            total_freight = total_freight - advance;
                        }
                        esc.addText(String.format("%s???", ZDoubleFormat.zFormat(total_freight.toString())));
                        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                        esc.addSetAbsolutePrintPosition((short) 12);
                        if (printEntity.getPayment_method() != null && printEntity.getPayment_method().equals("?????????")) {
                            esc.addText("[" + mutiFee + "]");
                        } else {
                            esc.addText("[" + printEntity.getPayment_method() + "]");
                        }
                    }
                }
//                if (isSign) {
//                    Double totalFee = StringUtils.getDoubleNotNull(printEntity.getFreight())
//                            + StringUtils.getDoubleNotNull(printEntity.getReceiving_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getHandling_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getForklift_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getPacking_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getUpstair_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getAdvance())
//                            + StringUtils.getDoubleNotNull(printEntity.getDelivery_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getValuation_fee())
//                            + StringUtils.getDoubleNotNull(printEntity.getCollection_fee())
//                            - StringUtils.getDoubleNotNull(printEntity.getCash_payment());
//                    esc.addText("????????????: " + ZDoubleFormat.zFormat(totalFee.toString()) + "???");
//                    esc.addPrintAndLineFeed();
//                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

                Double total = 0.0;
                Double daishou = 0.0;
                double allMoney = 0.0;
                if (printEntity.getTotal_freight() != null) {
                    total = printEntity.getTotal_freight();
                }
                if (printEntity.getCollection_fee() != null) {
                    daishou = printEntity.getCollection_fee();
                }
                if (printEntity.getPayment_method() != null) {
                    if (printEntity.getPayment_method().equals("??????")) {
                        allMoney = total + daishou;
                    } else if (printEntity.getPayment_method().equals("??????")) {
                        allMoney = daishou;
                    } else if (printEntity.getPayment_method().equals("?????????")) {
                        Double collect_payment = printEntity.getCollect_payment();
                        int tempx = 0;
                        if (collect_payment == null) {
                            tempx = 0;
                        } else {
                            tempx = Integer.parseInt(ZDoubleFormat.zFormat(String.valueOf(collect_payment)));
                        }
                        allMoney = tempx + daishou;
                    }
                }

                if (!isSign) {
                    esc.addText("     ??????: " + ZDoubleFormat.zFormat(allMoney + "") + "???");
                } else {
                    esc.addText("     ??????: " + ZDoubleFormat.zFormat(allMoney + "") + "???");
                }

                esc.addPrintAndLineFeed();
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                if (printEntity.getBarCode() != null) {
                    if (ZShrPrefencs.getInstance().getIsBarCode() == 1) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                        esc.addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION.BELOW);
                        // ????????????????????????????????????????????????
                        // ?????????????????????60???
                        esc.addSetBarcodeHeight((byte) 80);
                        // ???????????????????????????1
                        esc.addSetBarcodeWidth((byte) 2);
                        // ??????Code128???
                        String barCode = printEntity.getBarCode();
                        esc.addCODE128(esc.genCode128(StringUtils.getNullablString(barCode)));
                        esc.addPrintAndLineFeed();
                    }
                }

                if (printEntity.getRemarks() != null && !printEntity.getRemarks().equals("")) {
                    esc.addText("??????: " + printEntity.getRemarks());
                    esc.addPrintAndLineFeed();
                }

                if (isSign) {
                    esc.addText("?????????: ");
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                }

                int space = ZShrPrefencs.getInstance().getSpaceCount();

                for (int p = 0; p < space; p++) {
                    esc.addPrintAndLineFeed();
                }

                String notice = printEntity.getLogistics_notice();
                if (!isSign) {
                    if (notice != null && !notice.isEmpty()) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText("??????");
                        esc.addPrintAndLineFeed();
                        esc.addText(notice);
                    }
                }
                if (AppUserToken.getInstance().getResult().getLogistics_uuid() == 2306) {
                    esc.addPrintAndLineFeed();
                    esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                    esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
                    esc.addText("???????????????3??????");
                }
//            List<Declaration> notices = AppUserToken.getInstance().getDeclars();
//            if (notices != null && notices.size() != 0) {
//                for (int k = 0; k < notices.size(); k++) {
//                    esc.addText((k + 1) + "," + notices.get(k).getLogistics_notice());
//                    esc.addPrintAndLineFeed();
//                }
//            } else {
//                esc.addText("1,??????????????????????????????;?????????????????????????????????????????????");
//                esc.addPrintAndLineFeed();
//                esc.addText("2,????????????????????????????????????(??????????????????)??????????????????????????????????????????10??????????????????????????????6%??????????????????");
//                esc.addPrintAndLineFeed();
//                esc.addText("3,??????????????????????????????????????????????????????????????????");
//                esc.addPrintAndLineFeed();
//            }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addPrintAndLineFeed();
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                String pointName = AppUserToken.getInstance().getResult().getPoint_name();
                String pointPhone = printEntity.getStartPoint_phone();

                String endName = printEntity.getEndStation();
                String endPoint = printEntity.getPoint_owner_phone_o();


                esc.addText("????????????: " + StringUtils.getNullablString(pointName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (pointPhone != null) {
                    String tempaaa = pointPhone.split(",")[0];
                    esc.addText("????????????: " + tempaaa);
                }
                esc.addPrintAndLineFeed();
                if (pointPhone != null) {
                    if (pointPhone.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
                esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();
                esc.addText("????????????: " + StringUtils.getNullablString(endName));
                esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
                esc.addSetAbsolutePrintPosition((short) 10);

                if (endPoint != null) {
                    String tempxx = endPoint.split(",")[0];
                    esc.addText("????????????: " + tempxx);
                    esc.addPrintAndLineFeed();
                }

                if (endPoint != null) {
                    if (endPoint.split(",").length == 2) {
                        esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
                        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
                        esc.addText(pointPhone.split(",")[1]);
                        esc.addPrintAndLineFeed();
                    }
                }
                esc.addText("------------------------------------------------");
                esc.addPrintAndLineFeed();

                esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
                esc.addText(" ???????????????????????????" + "  www.chinniu.com");
                esc.addPrintAndLineFeed();
//                esc.addGeneratePlus(LabelCommand.FOOT.F2, (byte) 255, (byte) 255);
                esc.addPrintAndFeedLines((byte) 3);
                esc.addPrintAndFeedLines((byte) 1);
                esc.addCutPaper();

            }
            //for ????????????
            esc.addCutPaper();
            esc.addQueryPrinterStatus();
            Vector<Byte> datas = esc.getCommand();//????????????
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
        }
    }
}