def dining_hall_menus(ovt_menu, pines_menu):
    menu_differences = []
    for a in range(len(ovt_menu)):
        if ovt_menu[a] not in pines_menu and ovt_menu[a] not in menu_differences:
            menu_differences.append(ovt_menu[a])
    for a in range(len(pines_menu)):
        if pines_menu[a] not in ovt_menu and pines_menu[a] not in menu_differences:
            menu_differences.append(pines_menu[a])
    return menu_differences