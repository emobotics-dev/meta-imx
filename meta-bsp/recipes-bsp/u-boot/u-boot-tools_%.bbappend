# Disable CONFIG_CMD_LICENSE, license.h is not used by tools and
# generating it requires bin2header tool, which for target build
# is built with target tools and thus cannot be executed on host.
# Disable CONFIG_FIT_SIGNATURE, openssl is not always the same
# version, which the native tool is used on another Host.
SED_CONFIG_DISABLE = "CONFIG_CMD_LICENSE CONFIG_FIT_SIGNATURE CONFIG_EFI_LOADER"
SED_CONFIG_DISABLE_remove_x86 = "CONFIG_EFI_LOADER"
SED_CONFIG_DISABLE_remove_x86-64 = "CONFIG_EFI_LOADER"
SED_CONFIG_DISABLE_remove_arm = "CONFIG_EFI_LOADER"
SED_CONFIG_DISABLE_remove_armeb = "CONFIG_EFI_LOADER"
SED_CONFIG_DISABLE_remove_aarch64 = "CONFIG_EFI_LOADER"

# Reset BUILT_LDFLGGS to build u-boot-tools-native
# to remove dynamic-linker setting as uninative feature enabled.
# So this native tools uboot-mkimage can be copied out for standalone use.
export DEFAULT_BUILD_LDFLAGS = "-L${STAGING_LIBDIR_NATIVE} \
                        -L${STAGING_BASE_LIBDIR_NATIVE} \
                        -Wl,--enable-new-dtags \
                        -Wl,-rpath-link,${STAGING_LIBDIR_NATIVE} \
                        -Wl,-rpath-link,${STAGING_BASE_LIBDIR_NATIVE} \
                        -Wl,-rpath,${STAGING_LIBDIR_NATIVE} \
                        -Wl,-rpath,${STAGING_BASE_LIBDIR_NATIVE} \
                        -Wl,-O1"

EXTRA_OEMAKE_class-native = 'CC="${BUILD_CC} ${BUILD_CFLAGS} ${DEFAULT_BUILD_LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${DEFAULT_BUILD_LDFLAGS}" STRIP=true V=1'


do_compile() {
	oe_runmake sandbox_defconfig
	for config in ${SED_CONFIG_DISABLE}; do
		sed -i -e "s/$config=.*/# $config is not set/" .config
	done
	oe_runmake cross_tools NO_SDL=1
}

# Have to overwrite do_install() as uboot-fit_check_sign and fit_check_sign
#  are not generated as disable CONFIG_FIT_SIGNATURE.
do_install () {
	install -d ${D}${bindir}

	# mkimage
	install -m 0755 tools/mkimage ${D}${bindir}/uboot-mkimage
	ln -sf uboot-mkimage ${D}${bindir}/mkimage

	# mkenvimage
	install -m 0755 tools/mkenvimage ${D}${bindir}/uboot-mkenvimage
	ln -sf uboot-mkenvimage ${D}${bindir}/mkenvimage

	# dumpimage
	install -m 0755 tools/dumpimage ${D}${bindir}/uboot-dumpimage
	ln -sf uboot-dumpimage ${D}${bindir}/dumpimage
}


FILES_${PN}-mkimage_remove = "${bindir}/uboot-fit_check_sign ${bindir}/fit_check_sign"
