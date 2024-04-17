package com.girlkun.models.boss.list_boss.Mabu12h;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

public class ThuyTinh extends Boss {

    private long st;

    public ThuyTinh() throws Exception {
        super(Util.randomBossId(), BossesData.THUYTINH);
        this.cFlag = 1;
    }

    public void reward(Player plKill) {
//        short[] itemRan = Manager.itemIds_bossVuaHung;
//        short itemId = itemRan[0];
//        short idRanDom = (short) Util.nextInt(100);
//        if (idRanDom < 70) {
//            itemId = itemRan[Util.nextInt(itemRan.length - 1)];
//        } else if (idRanDom < 95) {
//            itemId = itemRan[Util.nextInt(itemRan.length)];
//        } else if (idRanDom < 100) {
//            itemId = 674;
//        }
//
//        int ranSL = Util.nextInt(1, 10);
//        if (itemId == 674) {
//            ranSL = Util.nextInt(1, 5);
//        }
//
//        for (int i = 0; i < ranSL; ++i) {
//            ItemMap it = new ItemMap(this.zone, itemId, 1, this.location.x + i * 20, this.zone.map.yPhysicInTop(this.location.x + i * 20, this.location.y - 24), plKill.id);
//            Service.getInstance().dropItemMap(this.zone, it);
//        }

    }

    public void chatM() {
        if (Util.isTrue(60, 61)) {
            super.chatM();
        } else if (this.bossAppearTogether != null && this.bossAppearTogether[this.currentLevel] != null) {
            Boss[] var1 = this.bossAppearTogether[this.currentLevel];
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Boss boss = var1[var3];
                if (boss.id == -300L && !boss.isDie()) {
                    this.chat("Trả Mị Nương lại cho ta");
                    boss.chat("Còn lâu á, chậm chân còn cái nịt");
                    break;
                }
            }

        }
    }

    public void active() {
        super.active();
    }

    public void joinMap() {
        super.joinMap();
        this.st = System.currentTimeMillis();
        Service.getInstance().changeFlag(this, 1);
    }

    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (damage >= 1000000) {
                damage = 1000000;
            }
            
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

    public void doneChatS() {
        Boss[] var1 = this.bossAppearTogether[this.currentLevel];
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Boss boss = var1[var3];
            if (boss.id == -30L) {
                boss.changeToTypePK();
                break;
            }
        }

    }

    public void changeToTypePK() {
        super.changeToTypePK();
        this.chat("Mau đền mạng cho thằng em trai ta");
    }
}
// public class ThuyTinh extends Boss {

//     public ThuyTinh() throws Exception {
//         super(Util.randomBossId(), BossesData.THUYTINH);
//     }
//     @Override
//     public void reward(Player plKill) {
//         byte randomDo = (byte) new Random().nextInt(Manager.itemIds_TL.length - 1);
//         byte randomNR = (byte) new Random().nextInt(Manager.itemIds_NR_SB.length);
//         byte randomc12 = (byte) new Random().nextInt(Manager.itemDC12.length -1);
//         if (Util.isTrue(1, 130)) {
//             if (Util.isTrue(1, 50)) {
//                 Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 561, 1, this.location.x, this.location.y, plKill.id));
//                 return;
//             }
//             Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, Manager.itemIds_TL[randomDo], 1, this.location.x, this.location.y, plKill.id));
//         } else
//         if (Util.isTrue(50, 100)) {
//             Service.gI().dropItemMap(this.zone,new ItemMap (Util.RaitiDoc12(zone, Manager.itemDC12[randomc12], 1, this.location.x, this.location.y, plKill.id)));
//             return;
//         }
//         else {
//             Service.gI().dropItemMap(this.zone, new ItemMap(zone, Manager.itemIds_NR_SB[randomNR], 1, this.location.x, this.location.y, plKill.id));
//         }
//         plKill.fightMabu.changePoint((byte) 20);
//     }
//         @Override
//     public void active() {
//         if (this.typePk == ConstPlayer.NON_PK) {
//             this.changeToTypePKPvP();
//         }
//         //this.hapThu();
//         this.attack();
//     }
//     @Override
//     public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
//         if (!this.isDie()) {
//             if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
//                 this.chat("Xí hụt");
//                 return 0;
//             }
//             damage = this.nPoint.subDameInjureWithDeff(damage);
//             if (!piercing && effectSkill.isShielding) {
//                 if (damage > nPoint.hpMax) {
//                     EffectSkillService.gI().breakShield(this);
//                 }
//                 damage = 1;
//             }
//             if (damage >= 1000000) {
//                 damage = 1000000;
//             }
//             this.nPoint.subHP(damage);
//             if (isDie()) {
//                 this.setDie(plAtt);
//                 die(plAtt);
//             }
//             return damage;
//         } else {
//             return 0;
//         }
//     }
// }
