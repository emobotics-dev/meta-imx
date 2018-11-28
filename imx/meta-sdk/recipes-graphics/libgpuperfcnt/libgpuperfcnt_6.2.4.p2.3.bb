DESCRIPTION = "A library to retrieve i.MX GPU performance data"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6dfb32a488e5fd6bae52fbf6c7ebb086"

SRC_URI[arm-fb.md5sum] = "c2647e062d093d6d0b03a4ff21dfd576"
SRC_URI[arm-fb.sha256sum] = "f4b251ea048f14764659980cf2922f7a952a9e256f515db82b027a5654323a02"

SRC_URI[arm-wayland.md5sum] = "e2dc4ce44f3d5ea4f48d2541b57e0569"
SRC_URI[arm-wayland.sha256sum] = "8623b3714c30efd82dd3198cd4cd779ae3e6c9e6ec2b5c0fabe976f0755a2210"

SRC_URI[arm-x11.md5sum] = "2d39e8f8f1961bd6461f4009881bfaeb"
SRC_URI[arm-x11.sha256sum] = "3bb181c3d1f7a1787e1ce5a95a148183174598bcfeeb58ba68feef38bc4e93ec"

SRC_URI[aarch64-fb.md5sum] = "2be91ed27d193159422eaba3cfc57564"
SRC_URI[aarch64-fb.sha256sum] = "ceac9a663dbc5b8801681611cba7e575a434cd9f7c704e0f54c78a1f82b366c0"

SRC_URI[aarch64-wayland.md5sum] = "ade203957583a5eadaa599986e2e885f"
SRC_URI[aarch64-wayland.sha256sum] = "521217e3e9f8fa2d424ef2102d1c4ab83fad9c725e08ee2bd2d5501d96eda056"

SRC_URI[aarch64-x11.md5sum] = "23057f7807545102fc94f085115a1b5f"
SRC_URI[aarch64-x11.sha256sum] = "90298f80ffd3f17cacd50f6bdd641293e1dad6176fbbb5b276ee181674558fed"

inherit fsl-eula-unpack2 fsl-eula-graphics

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

RDEPENDS_${PN} = "imx-gpu-viv"

# Compatible only with i.MX with GPU
COMPATIBLE_MACHINE        = "(^$)"
COMPATIBLE_MACHINE_imxgpu = "${MACHINE}"
