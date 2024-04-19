package com.girlkun.models.boss.list_boss.sontinhthuytinh;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.SkillService;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;
import com.girlkun.models.item.Item;

public class ThuyTinh extends Boss {

    public ThuyTinh() throws Exception {
        super(BossID.THUYTINH, BossesData.THUYTINH);
        super.cFlag = 1;
    }

    @Override
    public void reward(Player plKill) {
        ItemMap ctSonTinh = new ItemMap(this.zone, 422, 1, this.location.x, this.location.y, plKill.id);
        ItemMap ball1s = new ItemMap(this.zone, 2128, 1, this.location.x, this.location.y, plKill.id);
        ItemMap ball2s = new ItemMap(this.zone, 2129, 1, this.location.x, this.location.y, plKill.id);
        ItemMap ball3s = new ItemMap(this.zone, 2130, 1, this.location.x, this.location.y, plKill.id);
        ItemMap voi9Nga = new ItemMap(this.zone, 1325, 5, this.location.x, this.location.y, plKill.id);
        ItemMap ga9Cua = new ItemMap(this.zone, 1326, 5, this.location.x, this.location.y, plKill.id);
        ItemMap ngua9HongMao = new ItemMap(this.zone, 1327, 5, this.location.x, this.location.y, plKill.id);


        ctSonTinh.options.add(new Item.ItemOption(50, 350));// giap
        ctSonTinh.options.add(new Item.ItemOption(77, 350));// giap
        ctSonTinh.options.add(new Item.ItemOption(103, 350));// giap
        ctSonTinh.options.add(new Item.ItemOption(5, 50));// giap
        ctSonTinh.options.add(new Item.ItemOption(93, 1));// giap

        Service.gI().dropItemMap(this.zone, ctSonTinh);
        Service.gI().dropItemMap(this.zone, ball1s);
        Service.gI().dropItemMap(this.zone, ball2s);
        Service.gI().dropItemMap(this.zone, ball3s);
        Service.gI().dropItemMap(this.zone, voi9Nga);
        Service.gI().dropItemMap(this.zone, ga9Cua);
        Service.gI().dropItemMap(this.zone, ngua9HongMao);

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

    @Override
    public void active() {
        //System.out.println("activeTT");
        this.attack();
//       if(this.cFlag != 1){
//           Service.gI().changeFlag(this, 1);
//       }
        //    if (Util.canDoWithTime(st, 10_000)) {
        //         Service.gI().changeFlag(this, (byte) this.cFlag);
        //    }
        if (Util.canDoWithTime(st, 600000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }

    }

    @Override
    public void attack() {
        if (Util.canDoWithTime(this.lastTimeAttack, 1000)) {

            this.lastTimeAttack = System.currentTimeMillis();
            try {
                Player pl = null;
                if (bossStatus == BossStatus.ATTACK_FLAG) {
                    pl = getPlayerAttackFlag();
                } else {
                    pl = getPlayerAttack();
                }
                if (pl == null || pl.isDie()) {
                    return;
                }
                this.playerSkill.skillSelect = this.playerSkill.skills
                        .get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                    if (Util.isTrue(5, 20)) {
                        if (SkillUtil.isUseSkillChuong(this)) {
                            this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 200)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 70));
                        } else {
                            this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(10, 40)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 50));
                        }
                    }
                    SkillService.gI().useSkill(this, pl, null, null);
                    checkPlayerDie(pl);
                } else {
                    if (Util.isTrue(1, 2)) {
                        this.moveToPlayer(pl);
                    }
                }

            } catch (Exception ex) {
                return;
            }
        }
    }

    @Override
    public void joinMap() {

        super.joinMap(); // To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
        this.changeToTypeNonPK();

    }

    private long st;
    private long lastTimeBlame;
}
