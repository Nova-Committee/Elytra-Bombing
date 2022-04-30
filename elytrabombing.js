ll.registerPlugin("ElytraBombing", "Bomb them from the air!", [1, 0, 0])

mc.listen("onUseItem", function (player, item) {
    if (!player.inAir) {
        return;
    }
    if (item.isnull) {
        return;
    }
    if (!item.type.match(/flint_and_steel/)) {
        return;
    }
    let hasTNT = false;
    let slot = 0;
    const size = player.getInventory().size
    for (let i = 0; i < size; i++) {
        if (player.getInventory().getItem(i).type.match(/tnt/)) {
            hasTNT = true;
            slot = i;
            break;
        }
    }
    if (!hasTNT) {
        return;
    }
    const summoned = mc.runcmd("summon tnt " + player.pos.x + " " + (player.pos.y - 1) + " " + player.pos.z)
    if (summoned) {
        player.getInventory().removeItem(slot, 1)
    }
});