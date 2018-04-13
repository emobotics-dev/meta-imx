# Copyright 2017-2018 NXP

require recipes-bsp/firmware-imx/firmware-imx.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=6bd2df2910a2234bf6f419b7d2f531a3"

#BRCM firmware v1.141.100.6
IMX_FIRMWARE_SRC ?= "git://git.freescale.com/proprietary/imx-firmware.git;protocol=git"
SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
           ${IMX_FIRMWARE_SRC};branch=${SRCBRANCH};destsuffix=${S}/git "

SRC_URI[md5sum] = "c604583dd9c48bd8b3a06caf718e50bb"
SRC_URI[sha256sum] = "191c053bb5f4b75b52f83d84fbedad9bc478a46b0610a0d1e8ecd9fcbd5d0bbd"

#BRCM firmware git
SRCREV = "8ce9046f5058fdd2c5271f86ccfc61bc5a248ae3"

do_install_append() {
    # No need to do install for ddr & hdmi binaries
    if [ -d ${D}${base_libdir}/firmware/ddr ]; then
        rm -rf ${D}${base_libdir}/firmware/ddr
    fi
    if [ -d ${D}${base_libdir}/firmware/hdmi ]; then
        rm -rf ${D}${base_libdir}/firmware/hdmi
    fi

    # Don't install hifi4 related binary
    if [ -d ${D}${base_libdir}/firmware/hifi4 ]; then
        rm -rf ${D}${base_libdir}/firmware/hifi4
    fi
    # Don't install seco related binary
    if [ -d ${D}${base_libdir}/firmware/seco ]; then
        rm -rf ${D}${base_libdir}/firmware/seco
    fi

    #1FD_BCM89359
    install -d ${D}${base_libdir}/firmware/bcm/1FD_BCM89359
    cp -rfv git/brcm/1FD_BCM89359/*.bin ${D}${base_libdir}/firmware/bcm/1FD_BCM89359
    cp -rfv git/brcm/1FD_BCM89359/*.hcd ${D}${sysconfdir}/firmware/

    #1CX_BCM4356
    install -d ${D}${base_libdir}/firmware/bcm/1CX_BCM4356
    cp -rfv git/brcm/1CX_BCM4356/fw_bcmdhd.bin ${D}${base_libdir}/firmware/bcm/1CX_BCM4356
}

IS_MX8 = "0"
IS_MX8_mx8mq = "8mq"
IS_MX8_mx8qm = "8qm"
IS_MX8_mx8qxp = "8qx"
inherit deploy
addtask deploy before do_build after do_install
do_deploy () {
    # Deploy i.MX8 related firmware files
    if [ "${IS_MX8}" = "8mq" ]; then
        # Deploy ddr/synopsys
        for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
            install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
        done

        # Deploy hdmi/cadence
        install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${DEPLOYDIR}
    elif [ "${IS_MX8}" = "8qm" ]; then
        # Deploy hdmi/cadence
        install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${DEPLOYDIR}
        install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${DEPLOYDIR}
    elif [ "${IS_MX8}" = "8qx" ]; then
        # Deploy seco
        install -m 0644 ${S}/firmware/seco/ahab-container.img ${DEPLOYDIR}
    fi
}
